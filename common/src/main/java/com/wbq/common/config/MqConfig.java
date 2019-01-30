package com.wbq.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 *  * @author biqin.wu
 *  * @since 30 January 2019
 *  
 */
@Configuration
public class MqConfig {

    private static final Logger log = LoggerFactory.getLogger(MqConfig.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private AppConfig appConfig;

    @Bean
    public AmqpTemplate amqpTemplate() {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding(StandardCharsets.UTF_8.name());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.debug("message={} send fail replyCode={},replyText={},exchange={},routingKey={}", correlationId, replyCode, replyText, exchange, routingKey);
        });
        //消息确认
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.debug("message send to exchange success id={}", correlationData.getId());
            } else {
                log.debug("message send to exchange fail id={} reason={}", correlationData.getId(), cause);
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange(appConfig.getDirectExchange()).durable(true).build();
    }

    @Bean
    public Queue directQueue() {
        return QueueBuilder.durable(appConfig.getDirectQueue()).build();
    }

    @Bean
    public Binding directBinding(@Qualifier("directQueue") Queue queue, @Qualifier("directExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(appConfig.getDirectRoutingKey()).noargs();
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return (FanoutExchange) ExchangeBuilder.fanoutExchange(appConfig.getFanoutExchange()).durable(true).build();
    }

    @Bean
    public Queue fanoutQueue1() {
        return QueueBuilder.durable(appConfig.getFanoutQueue1()).build();
    }

    @Bean
    public Queue fanoutQueue2() {
        return QueueBuilder.durable(appConfig.getFanoutQueue2()).build();
    }

    @Bean
    public Binding binding1(@Qualifier("fanoutQueue1") Queue queue, @Qualifier("fanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding binding2(@Qualifier("fanoutQueue2") Queue queue, @Qualifier("fanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

}
