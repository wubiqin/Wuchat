package com.wbq.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: biqin.wu
 * @Date: 2019/1/13
 * @Time: 15:30
 * @Description:
 */
@Component
@Data
public class AppConfig {

    @Value("${chat.zk.address}")
    private String zkAddress;

    @Value("${chat.zk.timeout}")
    private Integer timeout;

    @Value("${chat.redis.address}")
    private String redisAddress;

    @Value("${chat.redis.port}")
    private Integer redisPort;

    private Integer redisMaxTotal;

    private Integer redisMaxIdle;

    @Value("${chat.redis.timeout}")
    private Integer redisTimeout;

    @Value("${chat.mq.directExchange}")
    private String directExchange;

    @Value("${chat.mq.directQueue}")
    private String directQueue;

    @Value("${chat.mq.fanoutExchange}")
    private String fanoutExchange;

    @Value("${chat.mq.fanoutQueue1}")
    private String fanoutQueue1;

    @Value("${chat.mq.fanoutQueue2}")
    private String fanoutQueue2;

    @Value("${chat.mq.directRoutingKey}")
    private String directRoutingKey;


}
