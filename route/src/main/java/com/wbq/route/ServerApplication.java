package com.wbq.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: biqin.wu
 * @Date: 2019/1/19
 * @Time: 22:07
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.wbq.*")
@Slf4j
public class ServerApplication {

    public static void main(String[] args) {
        log.info("server start .....");
        SpringApplication.run(ServerApplication.class, args);
    }

}
