package cn.neud.trace.note.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Configuration
public class RabbitCallBackConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        // 注入确认回调
        rabbitTemplate.setConfirmCallback(this);
        // 注入消息丢失回调
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        String id = correlationData != null ? correlationData.getId() : null;
        if (ack) {
            log.info("消息发送成功:correlationId({}))", id);
        } else {
            log.error("消息发送失败:correlationId({}),cause({})", id, s);
        }
    }

    // 消息丢失，在消息不可达目的地的时候才会触发
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        if (returnedMessage.getExchange().equals(MQConfig.SECKILL_DELAY_EXCHANGE)) {
            return;
        }
        log.error("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", returnedMessage.getExchange(), returnedMessage.getRoutingKey(), returnedMessage.getReplyCode(), returnedMessage.getReplyText(), returnedMessage.getMessage());
    }
}
