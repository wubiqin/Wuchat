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

    private String redisAddress;

    private Integer redisPort;

    private Integer redisMaxTotal;

    private Integer redisMaxIdle;

    private Integer redisTimeout;
}
