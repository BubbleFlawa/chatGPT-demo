package com.asleepyfish.manager;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.asleepyfish.entity.Message;
import com.asleepyfish.entity.RequestBody;
import com.asleepyfish.entity.ResponseBody;
import com.asleepyfish.mqtt.MqttPushClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: asleepyfish
 * @Date: 2023/3/3 14:00
 * @Description: OpenAiProxyService
 */
@Slf4j
@Service
public class ChatAIManager {
    @Autowired
    private MqttPushClient mqttPushClient;

    private static final String URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";

    @Async("normalThreadPool")
    public void chatAI(String input) {
        if (Objects.isNull(input)) {
            mqttPushClient.publish("answer", "请问你需要问什么问题呢？");
            return;
        }
        //设置消息
        List<Message> list = new ArrayList<>();
        Message message = new Message();
        //提问者为用户
        message.setRole("user");
        message.setContent(input);
        list.add(message);
        RequestBody requestBody = new RequestBody();
        requestBody.setModel("gpt-3.5-turbo");
        requestBody.setMessages(list);
        String data = JSON.toJSONString(requestBody);
        //开启代理
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10809));
        //发送请求
        String reqJSON = HttpRequest.post(URL)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .body(data)
                .setProxy(proxy)
                .execute()
                .body();
        //回传参数转换类型
        ResponseBody body = JSON.parseObject(reqJSON, ResponseBody.class);
        String answer = JSON.toJSONString(body.getChoices().get(0).getMessage().getContent());
        if (Objects.isNull(answer)) {
            mqttPushClient.publish("answer", "服务器异常");
            return;
        }
        mqttPushClient.publish("answer", answer);
        log.info("answer : " + answer);
    }
}
