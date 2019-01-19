package com.wbq.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author: biqin.wu
 * @Date: 2019/1/13
 * @Time: 17:15
 * @Description:
 */
@Component
@Slf4j
public class RedisUtils {

    @Resource
    private JedisPool jedisPool;

    public boolean set(String key, String value) {
        Jedis jedis = getResource();
        boolean flag = jedis.set(key, value) != null;
        close(jedis);
        return flag;
    }

    public boolean setnx(String key, String val) {
        Jedis jedis = getResource();
        long result = jedis.setnx(key, val);
        close(jedis);
        return result == 1;
    }

    public boolean setex(String key, String val, int seconds) {
        Jedis jedis = getResource();
        boolean flag = jedis.setex(key, seconds, val) != null;
        close(jedis);
        return flag;
    }

    public String get(String key) {
        Jedis jedis = getResource();
        String val = jedis.get(key);
        close(jedis);
        return val;
    }

    public void del(String key) {
        Jedis jedis = getResource();
        jedis.del(key);
    }

    private Jedis getResource() {
        Jedis jedis = jedisPool.getResource();
        if (jedis == null) {
            log.info("can't get connection from redis pool");
            throw new RuntimeException("can't get connection from redis pool");
        }
        return jedis;
    }

    private void close(Jedis jedis) {
        log.info("return connection to pool");
        jedis.close();
    }


}
