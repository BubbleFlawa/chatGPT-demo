package com.asleepyfish.controller;

import com.asleepyfish.entity.User;
import com.asleepyfish.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/")
    public String Login(@RequestBody User user) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            Session session = subject.getSession();
            session.setAttribute("user", user);
        } catch (AuthenticationException e) {
            return "登录失败";
        } catch (InvalidSessionException e) {
            throw new RuntimeException(e);
        }
        return "登录成功";
    }
}
