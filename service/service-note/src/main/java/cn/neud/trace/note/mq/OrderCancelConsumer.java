package cn.neud.trace.note.mq;

import cn.neud.trace.note.config.MQConfig;
import cn.neud.trace.note.model.entity.AdornmentOrder;
import cn.neud.trace.note.service.AdornmentOrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Component
public class OrderCancelConsumer {

    @Resource
    private AdornmentOrderService adornmentOrderService;

//    @RabbitListener(queues = MQConfig.SECKILL_DELAY_QUEUE)
//    public void listenOrderCancelQueueMessage(String orderId) {
//        log.info("====订单超时====");
//        // 取消订单
//        adornmentOrderService.cancelOrder(orderId);
//        log.info("====取消订单执行====");
//    }

    @RabbitListener(queues = MQConfig.SECKILL_DELAY_QUEUE)
    public void listenOrderCancelQueueMessage(
            String orderId,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
            Channel channel
    ) throws IOException {
//        String orderId = new String(message.getBody());
//        long deliveryTag = message.getMessageProperties().getHeader("amqp_deliveryTag");
        try {
            log.info("====订单超时====");
            // 取消订单
            adornmentOrderService.cancelOrder(orderId);
            log.info("====取消订单执行====");
            // 手动应答
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("处理订单异常", e);
            channel.basicNack(deliveryTag, false, true);
        }
    }

}
