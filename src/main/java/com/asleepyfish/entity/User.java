package com.asleepyfish.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String password;  //第一次输入的密码
    private String password1; //第二次输入的密码
    private String email;
}
