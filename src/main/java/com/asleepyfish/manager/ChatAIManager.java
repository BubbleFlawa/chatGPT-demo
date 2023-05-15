package com.asleepyfish.manager;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.asleepyfish.entity.Message;
import com.asleepyfish.entity.RequestBody;
import com.asleepyfish.entity.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;

/**
 * @Author: asleepyfish
 * @Date: 2023/3/3 14:00
 * @Description: OpenAiProxyService
 */
@Service
public class ChatAIManager {

    private static final String URL = "https://api.openai.com/v1/chat/completions";

    private static final String API_KEY = "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";

    public String chatAI(String input) {
    //return JSON.toJSONString("hello");
        String content = "";
        //输入
        if ("exit".equals(input)) {
            System.out.println("退出循环");
            return content;
        }
        //设置消息
        List<Message> list = new ArrayList<>();
        Message message = new Message();
        message.setRole("user");
        message.setContent(input);
        list.add(message);
        RequestBody requestBody = new RequestBody();
        requestBody.setModel("gpt-3.5-turbo");
        requestBody.setMessages(list);
        String data = JSON.toJSONString(requestBody);
        Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1", 10809));
        //发送请求
        String reqJSON = HttpRequest.post(URL)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .body(data)
                .setProxy(proxy)
                .execute()
                .body();
        System.out.println(reqJSON);
        //回传参数转换类型
        ResponseBody body = JSON.parseObject(reqJSON, ResponseBody.class);
//        content = body.getChoices().get(0).getMessage().getContent();
        //输出回值消息
        System.out.println(content);
//        return content;
        return JSON.toJSONString(body.getChoices().get(0).getMessage().getContent());
//        return body.getChoices().get(0).getMessage().getContent();

    }
}
