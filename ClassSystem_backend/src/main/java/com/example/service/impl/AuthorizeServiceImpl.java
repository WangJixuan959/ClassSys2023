package com.example.service.impl;

import com.example.entity.Account;
import com.example.mapper.UserMapper;
import com.example.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${spring.mail.username}")
    String from;

    @Resource
    UserMapper userMapper;

    @Resource
    MailSender mailSender;

    @Resource
    StringRedisTemplate template;

    BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null){
            throw new UsernameNotFoundException("用户名不能为空");
        }
        Account account = userMapper.findAccountByNameOrEmail(username);
        if(account == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }

    // 发送邮件验证码
    @Override
    public String sendValidateEmail(String email, String sessionId, Boolean hasAccount) {
        // 生成验证码；redis存储验证码；发送验证码到指定邮箱；发送失败则删除redis内部验证码；发送成功则取验证码进行匹配
        String key = "email:" + email + ":" + sessionId + ":" + hasAccount;
//        System.out.println("save_key:" + key);
//        System.out.println(key);
        if(Boolean.TRUE.equals(template.hasKey(key))){
            Long expire = Optional.ofNullable(template.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
//            System.out.println(expire);
            if(expire > 120){
                Long resend_time = expire - 120L;
//                System.out.println("还需" + resend_time + "s可重新发送邮件");
                return "请求频繁，请" + resend_time + "s后重试";
            }
        }
        Account account = userMapper.findAccountByNameOrEmail(email);
        if (account != null && !hasAccount){
            return "此邮箱已被其他用户注册";
        }
        if (account == null && hasAccount){
            return "未查询到此邮件地址账户信息";
        }
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("您的验证码是" + code);
        try {
            mailSender.send(message);
            template.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            return null;
        } catch (MailException e){
            e.printStackTrace();
            return "邮件发送失败，请检查邮件地址是否有效";
        }
    }

    @Override
    public String validateAndRegister(String username, String password, String email, String code, String sessionId) {
        String key = "email:" + email + ":" + sessionId + ":false";
//        System.out.println("validate_register_key:" + key);
        if(Boolean.TRUE.equals(template.hasKey(key))){
            String s = template.opsForValue().get(key);
            if(s == null){
                return "验证码失效，请重新发送";
            }
            if(s.equals(code)){
                Account account = userMapper.findAccountByNameOrEmail(username);
                if(account != null) return "此用户名已被注册，请更换用户名";
                template.delete(key);
                password = encoder.encode(password);
                if (userMapper.createAccount(username, password, email) > 0){
                    return null;
                } else {
                    return "数据异常，请联系管理员";
                }
            } else {
                return "验证码错误，请重试";
            }
        } else {
            return "请先进行邮箱验证";
        }
    }

    @Override
    public String validateForReset(String email, String code, String sessionId) {
        String key = "email:" + email + ":" + sessionId + ":true";
//        System.out.println("validate_reset_password_key:" + key);
        if(Boolean.TRUE.equals(template.hasKey(key))){
            String s = template.opsForValue().get(key);
            if(s == null){
                return "验证码失效，请重新发送";
            }
            if(s.equals(code)){
                template.delete(key);
                return null;
            } else {
                return "验证码错误，请重试";
            }
        } else {
            return "请先进行邮箱验证";
        }
    }

    @Override
    public Boolean resetPassword(String email, String password) {
        password = encoder.encode(password);
        return userMapper.resetPasswordByEmail(email, password) > 0;
    }
}
