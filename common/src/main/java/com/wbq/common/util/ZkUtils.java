package com.wbq.common.util;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * @author: biqin.wu
 * @Date: 2019/1/13
 * @Time: 15:15
 * @Description:
 */
@Slf4j
@Component
public class ZkUtils {

    @Resource
    private ZkClient zkClient;

    @Resource
    private Cache cache;

    public void createRootNode(String path) {
        Assert.isTrue(!Strings.isNullOrEmpty(path), "path can't be null or empty");
        boolean exist = zkClient.exists(path);
        if (exist) {
            log.info("根节点已存在 path={}", path);
            return;
        }
        log.info("创建根节点 path={}", path);
        zkClient.createPersistent(path);
    }

    public String buildPath(@NotNull String path, @NotNull String ip, @NotNull Integer rpcPort,
            @NotNull Integer httpPort) {
        StringBuilder node = new StringBuilder();
        node.append(path).append("/");
        node.append("-ip:").append(ip).append("rpcPort:").append(rpcPort).append("httpPort:").append(httpPort);
        return node.toString();
    }

    public void createNode(String path) {
        Assert.isTrue(!Strings.isNullOrEmpty(path), "path can't be null or empty");
        boolean exist = zkClient.exists(path);
        Assert.isTrue(!exist, "该节点已存在");
        log.info("创建临时节点 path={}", path);
        zkClient.createEphemeral(path);
    }

    public List<String> getChildren(String path) {
        Assert.isTrue(!Strings.isNullOrEmpty(path), "path can't be null or empty");
        List<String> childrenList = zkClient.getChildren(path);
        if (CollectionUtils.isEmpty(childrenList)) {
            return Collections.emptyList();
        }
        return childrenList;
    }

    public void subscribeEvent(String path) {
        zkClient.subscribeChildChanges(path, ((parentPath, currentChilds) -> {
            log.info("子节点变化 更新缓存 path={},childs={}", path, currentChilds);
            cache.update(currentChilds);
        }));
    }

    public boolean deleteNode(String path) {
        boolean isDelete = zkClient.delete(path);
        if (isDelete) {
            log.info("delete node success! path={}", path);
            return true;
        }
        return false;
    }

}
