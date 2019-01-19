package com.wbq.route.service;

import com.google.common.base.Strings;
import com.wbq.common.constant.Constant;
import com.wbq.common.model.User;
import com.wbq.common.util.RedisUtils;
import com.wbq.common.util.ThreadPoolUtil;
import com.wbq.common.util.ZkUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: biqin.wu
 * @Date: 2019/1/19
 * @Time: 20:13
 * @Description:
 */
@Service
public class RouteService {

    private static final String SLASH = "/";

    @Resource
    private ZkUtils zkUtils;

    @Resource
    private RedisUtils redisUtils;

    public boolean register(User user) {
        assert user != null;
        assert user.getUserName() != null;
        assert user.getPassword() != null;

        if (redisUtils.get(user.getUserName()) != null) {
            throw new IllegalArgumentException("account has been register!");
        }
        //这里可能出现并发
        boolean result = redisUtils.setnx(user.getUserName(), user.getPassword());
        if (result) {
            zkUtils.createRootNode(SLASH + user.getUserName());
            return Boolean.TRUE;
        }
        throw new IllegalArgumentException("account has been register!");
    }

    public List<String> getFriends(User user) {
        assert user != null;
        assert user.getUserName() != null;

        //todo 后期通过token校验
        //验证是否在线
        if (redisUtils.get(Constant.ONLINE + user.getUserName()) == null) {
            throw new UnsupportedOperationException("you are off lined");
        }
        return zkUtils.getChildren(SLASH + user.getUserName());
    }

    public void sendMsg(User from, List<String> toList, String msg) {
        assert !toList.isEmpty();
        assert !Strings.isNullOrEmpty(msg);
        assert from != null;

        List<String> ipList = toList.stream().map(it -> redisUtils.get(Constant.ONLINE + it)).collect(Collectors.toList());

        //todo 后期通过 消息队列异步形式发送消息
        ThreadPoolUtil.execute(() -> ipList.forEach(it -> {
            //发送消息 http


            //rpc
        }));
    }


}
