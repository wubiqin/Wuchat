package com.wbq.route.service;

import com.wbq.common.constant.Constant;
import com.wbq.common.model.User;
import com.wbq.common.util.RedisUtils;
import com.wbq.common.util.ZkUtils;
import com.wbq.route.ServerApplication;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: biqin.wu
 * @Date: 2019/1/19
 * @Time: 22:11
 * @Description:
 */
@SpringBootTest(classes = ServerApplication.class)
@RunWith(SpringRunner.class)
public class RouteServiceTest {

    @Resource
    private RouteService service;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ZkUtils zkUtils;

    User user = User.builder().userName("wubiqin").password("123456").build();

    List<String> list = Lists.newArrayList("s1", "s2");

    @Before
    public void before() {
        zkUtils.subscribeEvent(String.format("/%s", user.getUserName()));
    }

    @After
    public void after() {
        redisUtils.del(user.getUserName());
        zkUtils.unsubscribeChildChanges(String.format("/%s", user.getUserName()));
        list.forEach(it -> zkUtils.deleteNode(String.format("/format%s/%s", user.getUserName(), it)));
    }


    @Test
    public void register() {
        assert service.register(user);
    }

    @Test
    public void getFriends() {
        service.addFriend(user, list);
        List<String> list = service.getFriends(user);

        assert !list.isEmpty();

    }

    @Test
    public void sendMsg() {
        List<String> toList = Lists.newArrayList("xiaoming,xiaohong");
        redisUtils.setex(Constant.ONLINE + "xiaoming", "127.0.0.1:8080", 300);
        redisUtils.setex(Constant.ONLINE + "xiaohong", "127.0.0.1:8081", 300);
        service.sendMsg(user, toList, "hello world");
    }

    @Test
    public void addFriends() {
        service.addFriend(user, list);
    }
}