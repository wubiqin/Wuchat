package com.wbq.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: biqin.wu
 * @Date: 2019/1/19
 * @Time: 22:07
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.wbq.*")
@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true)
@Slf4j
public class ServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        log.info("server start .....");
        SpringApplication.run(ServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("server started success!");
    }
}
