package com.asleepyfish.manager;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import retrofit2.http.Header;

/**
 * @Author:huang_wq
 * @CreateTime:2023/5/15
 * @Description:
 */
//网关注解
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannelIOT")
public class MqttGateWayManage {
    /**
     *
     * @param topic
     * @param data
     */
    public void sendMessageToMqtt(@Header(MqttHeaders.TOPIC) String topic, String data){

    }
}
