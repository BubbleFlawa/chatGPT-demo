package com.asleepyfish.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class ResponseBody {
    /**
     * 回执消息集合
     */
    private List<Choices> choices;

    private String id;

    /**
     * 创建时间戳
     */
    private String created;

    /**
     * Gpt模型版本
     */
    private String model;
}
