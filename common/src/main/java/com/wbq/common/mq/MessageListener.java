package com.wbq.common.mq;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *  * @author biqin.wu
 *  * @since 30 January 2019
 *  
 */
@Component
public class MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);

    @RabbitListener(queues = {"fanoutQueue1"})
    public void fanoutQueue1Listener(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("fanoutQueue1" + new String(message.getBody()));
    }

    @RabbitListener(queues = {"fanoutQueue2"})
    public void fanoutQueue2Listener(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("fanoutQueue2" + new String(message.getBody()));
    }

    @RabbitListener(queues = {"directQueue"})
    public void directQueueListener(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("directQueue" + new String(message.getBody()));
    }

}
