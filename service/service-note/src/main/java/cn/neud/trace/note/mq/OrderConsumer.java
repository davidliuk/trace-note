package cn.neud.trace.note.mq;

import cn.neud.trace.note.config.MQConfig;
import cn.neud.trace.note.model.entity.AdornmentOrder;
import cn.neud.trace.note.service.AdornmentOrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Component
public class OrderConsumer {

    @Resource
    private AdornmentOrderService adornmentOrderService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MQConfig.SECKILL_QUEUE),
            exchange = @Exchange(name = MQConfig.SECKILL_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = "adornment.orders"
    ))
    public void listenTopicQueueMessage(
            AdornmentOrder adornmentOrder,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
            Channel channel
    ) throws IOException {
        try {
            // 创建订单
            adornmentOrderService.createAdornmentOrder(adornmentOrder);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("处理订单异常", e);
            channel.basicNack(deliveryTag, false, true);
        }
    }

//    // 消息手动应答
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = MQConfig.SECKILL_QUEUE),
//            exchange = @Exchange(name = MQConfig.SECKILL_EXCHANGE, type = ExchangeTypes.TOPIC),
//            key = "adornment.orders"
//    ), ackMode = "MANUAL")
//    public void listenTopicQueueMessage(
//            AdornmentOrder adornmentOrder,
//            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
//            Channel channel
//    ) throws IOException {
//        try {
//            // 创建订单
//            adornmentOrderService.createAdornmentOrder(adornmentOrder);
//            // 手动应答 "amqp_deliveryTag"
//            channel.basicAck(deliveryTag, false);
//        } catch (Exception e) {
//            log.error("处理订单异常", e);
//            // 拒绝消息
//            channel.basicReject(deliveryTag, false);
//        }
//    }

// 消息重试
// @RabbitListener(bindings = @QueueBinding(
//         value = @Queue(name = MQConfig.SECKILL_QUEUE),
//         exchange = @Exchange(name = MQConfig.SECKILL_EXCHANGE, type = ExchangeTypes.TOPIC),
//         key = "adornment.orders"
// ))
// public void listenTopicQueueMessage(AdornmentOrder adornmentOrder, Channel channel, Message message) {
//     try {
//         // 创建订单
//         adornmentOrderService.createAdornmentOrder(adornmentOrder);
//         // 手动应答
//         channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//     } catch (Exception e) {
//         log.error("处理订单异常", e);
//         // 拒绝消息
//         channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//         // 重试
//         channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//     }
// }

}
