package com.asleepyfish.example.entity;

import lombok.Data;

@Data
public class Choices {
    /**
     * 消息响应
     */
    private Message message;

    /**
     * 如果是stop代表发送完成
     */
    private String finish_reason;

    private Integer index;
}
