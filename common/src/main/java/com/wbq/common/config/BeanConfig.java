package com.wbq.common.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * @author: biqin.wu
 * @Date: 2019/1/13
 * @Time: 23:46
 * @Description:
 */
@Configuration
public class BeanConfig {

    @Resource
    private AppConfig appConfig;

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(appConfig.getZkAddress(), appConfig.getTimeout());
    }

    @Bean
    public JedisPool jedisConnectionFactory() {
        return new JedisPool(jedisPoolConfig(), appConfig.getRedisAddress(), appConfig.getRedisPort(),
                appConfig.getRedisTimeout());
    }

    @Bean
    public <T, R> LoadingCache<T, R> loadingCache() {
        return CacheBuilder.newBuilder().build(new CacheLoader<T, R>() {
            @Override
            public R load(T t) throws Exception {
                return null;
            }
        });
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1024);
        config.setMaxIdle(1000);
        config.setMaxWaitMillis(120000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        return config;
    }
}
