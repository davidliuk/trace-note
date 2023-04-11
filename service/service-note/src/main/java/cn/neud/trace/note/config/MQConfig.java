package cn.neud.trace.note.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MQConfig {

    // 秒杀交换机
    public static final String SECKILL_EXCHANGE = "seckill.exchange";
    // 秒杀延迟交换机
    public static final String SECKILL_DELAY_EXCHANGE = "seckill.delay.exchange";
    // 秒杀队列
    public static final String SECKILL_QUEUE = "seckill.queue";
    // 秒杀延迟队列
    public static final String SECKILL_DELAY_QUEUE = "seckill.delay.queue";

    public static final String SECKILL_DELAY_KEY = "cancel";

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public CustomExchange delayExchange() {
        return new CustomExchange(SECKILL_DELAY_EXCHANGE, "x-delayed-message", true, false, new HashMap<String, Object>() {{
            put("x-delayed-type", "direct"); // 延迟类型：直接交换
        }});
    }

    @Bean
    public Queue delayQueue() {
        return new Queue(SECKILL_DELAY_QUEUE, true);
    }

    @Bean
    public Binding delayBinding() {
        return new Binding(SECKILL_DELAY_QUEUE, Binding.DestinationType.QUEUE, SECKILL_DELAY_EXCHANGE, SECKILL_DELAY_KEY, null);
    }

}
