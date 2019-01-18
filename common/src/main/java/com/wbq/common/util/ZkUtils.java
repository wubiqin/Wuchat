package com.wbq.common.util;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
            log.info("节点已存在 path={}", path);
            return;
        }
        log.info("创建节点 path={}", path);
        zkClient.createPersistent(path);
    }

    /**
     * 创建临时节点 过期后自动删除
     *
     * @param path zk node path
     */
    public void createTempNode(String path) {
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

    public List<String> subscribeEvent(String path) {
        return zkClient.subscribeChildChanges(path,
                ((parentPath, currentChilds) -> log.info("子节点更新  path={},childs={}", path, currentChilds)));
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
