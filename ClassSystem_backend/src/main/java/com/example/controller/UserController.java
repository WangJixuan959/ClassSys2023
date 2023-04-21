package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public RestBean<UserInfo> me(@SessionAttribute("userinfo") UserInfo userInfo ){
        return RestBean.success(userInfo);
    }
}
