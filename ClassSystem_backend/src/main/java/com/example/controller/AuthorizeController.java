package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String USERNAME_REGEX = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";

    @Resource
    AuthorizeService authorizeService;

    @PostMapping("/valid-register-email")
    public RestBean<String> validateRegisterEmail(@Pattern (regexp = EMAIL_REGEX)@RequestParam("email") String email,
                                          HttpSession session){
        System.out.println(session.getId());
        String s = authorizeService.sendValidateEmail(email, session.getId(), false);
        if(s == null){
            return RestBean.success("注册邮件发送成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/valid-reset-email")
    public RestBean<String> validateResetEmail(@Pattern (regexp = EMAIL_REGEX)@RequestParam("email") String email,
                                          HttpSession session){
        System.out.println(session.getId());
        String s = authorizeService.sendValidateEmail(email, session.getId(), true);
        if(s == null){
            return RestBean.success("重置密码邮件发送成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/register")
    public RestBean<String> registerUser(@Pattern(regexp = USERNAME_REGEX) @Length(min = 2, max = 8) @RequestParam("username") String username,
                                         @Length(min = 6, max = 16) @RequestParam("password") String password,
                                         @Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                         @Length(max = 6) @RequestParam("code") String code,
                                         HttpSession session){
        System.out.println(session.getId());
        String s = authorizeService.validateAndRegister(username, password, email, code, session.getId());
        if(s == null){
            return RestBean.success("注册成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/start-reset")
    public RestBean<String> startReset(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                       @Length(max = 6) @RequestParam("code") String code,
                                       HttpSession session){
        String s = authorizeService.validateForReset(email, code, session.getId());
        if(s == null){
            session.setAttribute("reset-password", email);
            return RestBean.success("邮箱验证成功，请重置密码");
        } else {
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/do-reset")
    public RestBean<String> doResetPassword(@Length(min = 6, max = 16) @RequestParam("password") String password,
                                    HttpSession session){
        String email = (String) session.getAttribute("reset-password");
//        System.out.println(email);
        if(email == null){
            return RestBean.failure(400, "请先完成邮箱验证再重置密码");
        } else if(authorizeService.resetPassword(email, password)){
            session.removeAttribute("reset-password");
            return RestBean.success("密码重置成功");
        } else {
            return RestBean.failure(500, "系统错误，请联系管理员");
        }
    }
}
