package com.asleepyfish;

import io.github.asleepyfish.annotation.EnableChatGPT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableChatGPT
public class ChatGPTDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatGPTDemoApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  chatAI后台启动成功   ლ(´ڡ`ლ)ﾞ ");
    }

}
