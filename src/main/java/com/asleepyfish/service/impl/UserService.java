package com.asleepyfish.service.impl;

import com.asleepyfish.entity.User;
import com.asleepyfish.mapper.UserMapper;
import com.asleepyfish.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService implements IUserService {
    @Resource
    private UserMapper mapper;
    @Override
    public User getUser(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("name",name);
        return mapper.selectOne(wrapper);
    }
}
