package com.asleepyfish.entity;

import lombok.Data;

@Data
public class Message {
    /**
     * 角色 --具体以何种角色回答问题
     */
    private String role;

    /**
     * 问题
     */
    private String content;
}

