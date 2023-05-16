package com.asleepyfish.controller;

import com.asleepyfish.manager.ChatAIManager;
import io.github.asleepyfish.config.ChatGPTProperties;
import io.github.asleepyfish.service.OpenAiProxyService;
import io.github.asleepyfish.util.OpenAiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

/**
 * @Author: huang_wq
 * @Date: 2023-05-12 14:44
 * @Description: ChatGPTController
 */
@Controller
@RequestMapping("/chat")
public class ChatGPTController {
    @Autowired
    private ChatAIManager chatAIManager;

    /**
     * 会话页面
     */
    @GetMapping("/chatWeb")
    public String chatWeb() {
        return "indexmqtt.html";
    }

    /**
     * chat会话
     *
     * @param input
     * @return
     */
    @GetMapping("/chatAI")
    @ResponseBody
    public String chatAI(@RequestParam("input") String input) {
        chatAIManager.chatAI(input);
        return null;
    }

    @GetMapping("/register")
    public String register() {
        return "";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("success.html")
    public String successPage(HttpSession session, Model model) {
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            return "success";
        } else {
            model.addAttribute("msg", "请登录");
            return "login";
        }
    }

    @GetMapping("/success")
    public String success(HttpSession session, Model model) {
        return "success";
    }


    @GetMapping("/websocket")
    public String websocket(HttpSession session, Model model) {
        return "websocket";
    }


    /**
     * 普通问答
     */
    @GetMapping("/chat")
    public List<String> chat(String content) {
        return OpenAiUtils.createChatCompletion(content);
    }

    /**
     * 流式问答，返回到控制台
     */
    @GetMapping("/streamChat")
    public void streamChat(String content) {
        // OpenAiUtils.createStreamChatCompletion(content, System.out);
        // 下面的默认和上面这句代码一样，是输出结果到控制台
        OpenAiUtils.createStreamChatCompletion(content);
    }

    /**
     * 流式问答，输出结果到WEB浏览器端
     */
    @GetMapping("/streamChatWithWeb")
    public void streamChatWithWeb(String content, HttpServletResponse response) throws IOException, InterruptedException {
        // 需要指定response的ContentType为流式输出，且字符编码为UTF-8
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        // 禁用缓存
        response.setHeader("Cache-Control", "no-cache");
        OpenAiUtils.createStreamChatCompletion(content, response.getOutputStream());
    }

    /**
     * 下载图片
     */
    @GetMapping("/downloadImage")
    public void downloadImage(String prompt, HttpServletResponse response) {
        OpenAiUtils.downloadImage(prompt, response);
    }

    @GetMapping("/billingUsage")
    public void billingUsage() {
        String monthUsage = OpenAiUtils.billingUsage("2023-04-01", "2023-05-01");
        System.out.println("四月使用：" + monthUsage + "美元");
        String totalUsage = OpenAiUtils.billingUsage();
        System.out.println("一共使用：" + totalUsage + "美元");
    }

    /**
     * 自定义Token使用（解决单个SpringBoot项目中只能指定唯一的Token[sk-xxxxxxxxxxxxx]的问题，现在可以自定义ChatGPTProperties内容，添加更多的Token实例）
     */
    @GetMapping("/customToken")
    public void customToken() {
        ChatGPTProperties chatGPTProperties = new ChatGPTProperties();
        chatGPTProperties.setToken("sk-002xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        chatGPTProperties.setProxyHost("127.0.0.1");
        chatGPTProperties.setProxyPort(7890);
        OpenAiProxyService openAiProxyService = new OpenAiProxyService(chatGPTProperties, Duration.ZERO);
        // 直接使用new出来的openAiProxyService来调用方法，每个OpenAiProxyService都拥有自己的Token。
        // 这样在一个SpringBoot项目中，就可以有多个Token，可以有更多的免费额度供使用了
        openAiProxyService.createStreamChatCompletion("Java的三大特性是什么");
    }

}
