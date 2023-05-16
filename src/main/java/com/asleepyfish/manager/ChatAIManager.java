package com.asleepyfish.manager;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.asleepyfish.entity.Mes;
import com.asleepyfish.entity.Message;
import com.asleepyfish.entity.RequestBody;
import com.asleepyfish.entity.ResponseBody;
import com.asleepyfish.mqtt.MqttPushClient;
import com.asleepyfish.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.CollectionUtils;
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
    @Autowired
    private RedisService redisService;

    private static final String URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";

    @Async("normalThreadPool")
    public void chatAI(String input) {
        if (Objects.isNull(input)) {
            mqttPushClient.publish("answer", "请问你需要问什么问题呢？");
            return;
        }
        Mes mes = JSON.parseObject(input, Mes.class);
        if (Objects.isNull(mes.getName())) {
            mqttPushClient.publish("answer", "请登录");
            return;
        }
        if (Objects.isNull(mes.getMessage())) {
            mqttPushClient.publish("answer", "请问你需要问什么问题呢？");
            return;
        }

        List<List<Message>> lists = redisService.lrange(mes.getName());
        List<Message> list;
        if (CollectionUtils.isEmpty(lists)) {
            list = new ArrayList<>();
        } else {
            list = lists.get(0);
        }
        Message message = new Message();
        //提问者为用户
        message.setRole("user");
        message.setContent(mes.getMessage());
        list.add(message);

        String data = getMessageList(list);

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
        if (Objects.isNull(body) || Objects.isNull(body.getChoices())) {
            mqttPushClient.publish("answer", "服务器异常");
            return;
        }
        String answer = JSON.toJSONString(body.getChoices().get(0).getMessage().getContent());
        if (Objects.isNull(answer)) {
            mqttPushClient.publish("answer", "服务器异常");
            return;
        }
        //发送消息
        mqttPushClient.publish("answer", answer);
        //缓存
        list.add(body.getChoices().get(0).getMessage());
        redisService.lpush(mes.getName(), list, 0);
        log.info("answer : " + answer);
    }

    /**
     * redis缓存实现对话联系上下文
     *
     * @param list
     * @return
     */
    private String getMessageList(List<Message> list) {
        RequestBody requestBody = new RequestBody();
        requestBody.setModel("gpt-3.5-turbo");
        requestBody.setMessages(list);
        String data;
        if (CollectionUtils.isEmpty(list) || list.size() < 6) {
            //设置消息
            data = JSON.toJSONString(requestBody);
        } else {
            list.remove(0);
            data = JSON.toJSONString(requestBody);
        }
        return data;
    }
}
