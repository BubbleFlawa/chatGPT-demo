package com.asleepyfish.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author:huang_wq
 * @CreateTime:2023/5/15
 * @Description: 读取yml类
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.mqtt")
public class MqttConfig {
    private String url;
    private String clientId;
    private String topics;
    private String username;
    private String password;
    private String timeout;
    private String keepalive;

}
