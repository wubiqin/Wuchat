package com.wbq.common.mq;

import com.wbq.common.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *  * @author biqin.wu
 *  * @since 30 January 2019
 *  
 */
@Component
public class MessageSender {
    private static final Logger log = LoggerFactory.getLogger(MessageSender.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routingKey, String msg) {
        log.info("send msg exchange={},routingKey={} msg={}", exchange, routingKey, msg);
        CorrelationData correlationData = new CorrelationData(UUIDGenerator.uuid());
        rabbitTemplate.convertAndSend(exchange, routingKey, msg, correlationData);
    }


}
