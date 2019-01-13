package com.wbq.common.util;

import com.google.common.base.Splitter;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.wbq.common.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: biqin.wu
 * @Date: 2019/1/13
 * @Time: 16:21
 * @Description:
 */
@Component
@Slf4j
public class Cache {

    private static final Splitter splitter = Splitter.on("-");

    @Resource
    private LoadingCache<String, String> cache;

    @Resource
    private ZkClient zkClient;

    @Resource
    private AppConfig config;

    public void add(String key) {
        cache.put(key, key);
    }

    public void update(List<String> childrenList) {
        if (CollectionUtils.isEmpty(childrenList)) {
            return;
        }
        cache.invalidateAll();
        updateCache(childrenList);
    }

    private void updateCache(List<String> childrenList) {
        childrenList.forEach(path -> {
            Iterable<String> iterable = splitter.omitEmptyStrings().split(path);
            List<String> strings = Lists.newArrayList(iterable);
            if (strings.size() > 1) {
                String address = strings.get(1);
                add(address);
            }
        });
    }

    public List<String> getAll() {
        if (cache.size() > 0) {
            log.info("get server from cache");
            return new ArrayList<>(cache.asMap().keySet());
        }

        List<String> childrenList = zkClient.getChildren(config.getPath());
        if (CollectionUtils.isEmpty(childrenList)) {
            return Collections.emptyList();
        }
        updateCache(childrenList);

        return childrenList;
    }
}
