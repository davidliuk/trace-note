package cn.neud.trace.note.service.impl;

import cn.neud.trace.note.service.SeckillAdornmentService;
import cn.neud.trace.note.service.AdornmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.entity.SeckillAdornment;
import cn.neud.trace.note.model.entity.Adornment;
import cn.neud.trace.note.mapper.AdornmentMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static cn.neud.trace.note.constant.RedisConstant.SECKILL_STOCK_KEY;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author David
 * @since 2021-12-22
 */
@Service
public class AdornmentServiceImpl extends ServiceImpl<AdornmentMapper, Adornment> implements AdornmentService {

    @Resource
    private SeckillAdornmentService seckillAdornmentService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryAdornmentOfTrace(Long traceId) {
        // 查询装扮物信息
        List<Adornment> adornments = getBaseMapper().queryAdornmentOfTrace(traceId);
        // 返回结果
        return Result.ok(adornments);
    }

    @Override
    @Transactional
    public void addSeckillAdornment(Adornment adornment) {
        // 保存装扮物
        save(adornment);
        // 保存秒杀信息
        SeckillAdornment seckillAdornment = new SeckillAdornment();
        seckillAdornment.setAdornmentId(adornment.getId());
        seckillAdornment.setStock(adornment.getStock());
        seckillAdornment.setBeginTime(adornment.getBeginTime());
        seckillAdornment.setEndTime(adornment.getEndTime());
        seckillAdornment.setPayValue(adornment.getPayValue());
        seckillAdornment.setActualValue(adornment.getActualValue());
        seckillAdornmentService.save(seckillAdornment);
        // 保存秒杀库存到Redis中
        stringRedisTemplate.opsForValue().set(SECKILL_STOCK_KEY + adornment.getId(), adornment.getStock().toString());
    }
}
