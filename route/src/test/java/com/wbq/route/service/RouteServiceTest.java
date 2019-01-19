package com.wbq.route.service;

import com.wbq.common.model.User;
import com.wbq.route.ServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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

    @Test
    public void register() {
        User user = User.builder().userName("wubiqin").password("123456").build();
        assert service.register(user);
    }

    @Test
    public void getFriends() {
    }

    @Test
    public void sendMsg() {
    }
}