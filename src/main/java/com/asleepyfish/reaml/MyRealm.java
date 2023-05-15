package com.asleepyfish.reaml;

import com.asleepyfish.entity.User;
import com.asleepyfish.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String name = usernamePasswordToken.getUsername();
        User user = userService.getUser(name);
        if (!Objects.isNull(user)) {
            return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), name);
        } else {
            return null;
        }
    }
}