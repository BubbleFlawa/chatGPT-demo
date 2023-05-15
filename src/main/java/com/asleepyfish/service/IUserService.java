package com.asleepyfish.service;

import com.asleepyfish.entity.User;
import org.springframework.stereotype.Service;

public interface IUserService {
    User getUser(String name);
}
