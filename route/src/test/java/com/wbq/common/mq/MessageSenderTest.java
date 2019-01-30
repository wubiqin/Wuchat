package com.wbq.common.mq;

import com.wbq.common.config.AppConfig;
import com.wbq.route.ServerApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 *  * @author biqin.wu
 *  * @since 30 January 2019
 *  
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@Slf4j
public class MessageSenderTest {

    @Resource
    private MessageSender messageSender;

    @Resource
    private AppConfig appConfig;

    @Test
    public void send() {
        messageSender.send(appConfig.getFanoutExchange(), "", "fanout test send");

        messageSender.send(appConfig.getDirectExchange(), appConfig.getDirectRoutingKey(), "test direct");
    }
}