package com.asleepyfish.mqtt;

import com.asleepyfish.manager.ChatAIManager;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:lxb
 * @Date：2023/5/15 9:59
 */
@Slf4j
@Component
public class PushCallback implements MqttCallback {

    @Autowired
    private ChatAIManager chatAIManager;
    @Autowired
    private MqttConfiguration mqttConfiguration;

    /**
     * 连接丢失后，一般在这里面进行重连
     *
     * @param cause
     */
    @Override
    public void connectionLost(Throwable cause) {
        log.info("连接断开，正在重连");
        MqttPushClient mqttPushClient = mqttConfiguration.getMqttPushClient();
        if (null != mqttPushClient) {
            mqttPushClient.connect(mqttConfiguration.getHost(), mqttConfiguration.getClientid(), mqttConfiguration.getUsername(),
                    mqttConfiguration.getPassword(), mqttConfiguration.getTimeout(), mqttConfiguration.getKeepalive());
            log.info("已重连");
        }

    }

    /**
     * 发送消息，消息到达后处理方法
     *
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    /**
     * 订阅主题接收到消息处理方法
     *
     * @param topic
     * @param message
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        if ("question".equals(topic)) {
            chatAIManager.chatAI(new String(message.getPayload()));
        }
        // subscribe后得到的消息会执行到这里面,这里在控制台有输出
        //log.info("接收消息主题 : " + topic);
        //log.info("接收消息Qos : " + message.getQos());
        //log.info("接收消息内容 : " + new String(message.getPayload()));
        log.info("topic : " + new String(message.getPayload()));
    }

}