package com.asleepyfish.test;

import com.asleepyfish.manager.ChatAIManager;
import io.github.asleepyfish.util.OpenAiUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: asleepyfish
 * @Date: 2023/2/14 10:18
 * @Description: ChatGPTTest
 */
@SpringBootTest
public class ChatGPTTest {
    @Autowired
    private ChatAIManager manager;
    @Test
    public void testChatGPT() {
        OpenAiUtils.createCompletion("世界上最高的山峰是什么？").forEach(System.out::println);
    }

    @Test
    public void testGenerateImg() {
        OpenAiUtils.createImage("英短").forEach(System.out::println);
    }

//    @Test
//    public void testChatAI(){
//        String context = manager.chatAI("你好");
//        System.out.println(context);
//    }
}
