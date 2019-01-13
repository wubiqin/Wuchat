package com.wbq.route.controller;

import com.wbq.common.model.Response;
import com.wbq.common.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * \* @author: biqin.wu \* @Date: 2019/1/13 \* @Time: 14:36 \* @Description: \
 */
@RestController
@RequestMapping("/chat/route")
public class RouteController {

    // 注册
    @PostMapping("/register")
    public Response register(@RequestBody User user, HttpServletRequest request) {
        return null;
    }

    // 上线

    // 下线

    // chat
}
