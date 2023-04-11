package cn.neud.trace.note.service.impl;

import cn.neud.trace.note.config.MQConfig;
import cn.neud.trace.note.service.SeckillAdornmentService;
import cn.neud.trace.note.service.AdornmentOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.entity.AdornmentOrder;
import cn.neud.trace.note.mapper.AdornmentOrderMapper;
import cn.neud.trace.note.utils.RedisIdWorker;
import cn.neud.trace.note.utils.UserLocal;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author David
 * @since 2021-12-22
 */
@Slf4j
@Service
public class AdornmentOrderServiceImpl extends ServiceImpl<AdornmentOrderMapper, AdornmentOrder> implements AdornmentOrderService {

    @Resource
    private SeckillAdornmentService seckillAdornmentService;

    @Resource
    private RedisIdWorker redisIdWorker;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RabbitTemplate rabbitTemplate;

    private static final DefaultRedisScript<Long> SECKILL_SCRIPT = new DefaultRedisScript<Long>() {{
        setLocation(new ClassPathResource("seckill.lua"));
        setResultType(Long.class);
    }};

    private static final DefaultRedisScript<Long> CANCEL_SCRIPT = new DefaultRedisScript<Long>() {{
        setLocation(new ClassPathResource("cancelOrder.lua"));
        setResultType(Long.class);
    }};

    public void createAdornmentOrder(AdornmentOrder adornmentOrder) {
        Long userId = adornmentOrder.getUserId();
        Long adornmentId = adornmentOrder.getAdornmentId();
        // 创建锁对象
        RLock redisLock = redissonClient.getLock("lock:order:" + userId);
        // 尝试获取锁
        boolean isLock = redisLock.tryLock();
        // 判断
        if (!isLock) {
            // 获取锁失败，直接返回失败或者重试
            log.error("不允许重复下单！");
            return;
        }

        try {
            // 5.1.查询订单
            int count = query().eq("user_id", userId).eq("adornment_id", adornmentId).eq("status", 2).count();
            // 5.2.判断是否存在
            if (count > 0) {
                // 用户已经购买过了
                log.error("不允许重复下单！");
                return;
            }

            // 6.扣减库存
            boolean success = seckillAdornmentService.update()
                    .setSql("stock = stock - 1") // set stock = stock - 1
                    .eq("adornment_id", adornmentId).gt("stock", 0) // where id = ? and stock > 0
                    .update();
            if (!success) {
                // 扣减失败
                log.error("库存不足！");
                return;
            }

            // 7.创建订单
            save(adornmentOrder);
        } finally {
            // 释放锁
            redisLock.unlock();
        }
    }

    @Override
    public void cancelOrder(String orderId) {
        // 1.查询订单
        AdornmentOrder order = getById(orderId);
        // 2.判断订单状态
        if (order == null || order.getStatus() != 1) {
            // 订单已经支付
            return;
        }
        // 3.修改订单状态
        update().set("status", 4).eq("id", order.getId()).update();
        // 4.恢复库存和记录
        stringRedisTemplate.execute(
                CANCEL_SCRIPT,
                Collections.emptyList(),
                order.getAdornmentId().toString(), order.getUserId().toString()
        );
    }

    @Override
    public Result seckillAdornment(Long adornmentId) {
        Long userId = UserLocal.getUser().getId();
        long orderId = redisIdWorker.nextId("order");
        log.info("订单号生成成功，id: {}", orderId);
        // 1.执行lua脚本
        Long result = stringRedisTemplate.execute(
                SECKILL_SCRIPT,
                Collections.emptyList(),
                adornmentId.toString(), userId.toString(), String.valueOf(orderId)
        );
        assert result != null;
        int r = result.intValue();
        // 2.判断结果是否为0
        if (r != 0) {
            // 2.1.不为0 ，代表没有购买资格
            return Result.fail(r == 1 ? "库存不足" : "不能重复下单");
        }
        AdornmentOrder order = new AdornmentOrder() {{
            setId(orderId);
            setUserId(userId);
            setAdornmentId(adornmentId);
            setStatus(1);
        }};
        // 先发送延迟队列消息，再发送订单消息；保证订单一定会被砍
        rabbitTemplate.convertAndSend(MQConfig.SECKILL_DELAY_EXCHANGE, MQConfig.SECKILL_DELAY_KEY, orderId, message -> {
            message.getMessageProperties().setHeader("x-delay", 3 * 1000);
//            message.getMessageProperties().setDelay(1000);
            return message;
        });
        rabbitTemplate.convertAndSend(MQConfig.SECKILL_EXCHANGE, "adornment.orders", order);
        // 3.返回订单id
        return Result.ok(orderId);
    }
}
