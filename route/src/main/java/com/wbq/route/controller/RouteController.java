package com.wbq.route.controller;

import com.wbq.common.model.Response;
import com.wbq.common.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * \* @author: biqin.wu \* @Date: 2019/1/13 \* @Time: 14:36 \* @Description: \
 */
@RestController
@RequestMapping("/chat/route")
public class RouteController {

    // 注册
    @PostMapping("/register")
    public Response register(@RequestBody User user) {
        return null;
    }

    // 上线
    @PostMapping("/online")
    public Response online(@RequestBody User user, HttpServletRequest request) {
        return null;
    }

    // 下线
    @GetMapping("/offline")
    public Response offline(@RequestBody User user) {
        return null;
    }

    // chat
    @GetMapping("/")
    public Response sendMsg(String user, String msg) {
        assert msg != null;
        return null;
    }

}
