package com.wsw.cloud.ai.service.chat.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

/**
 * @Author wangsongwen
 * @Date 2025/10/19 21:39
 * @Description:
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource(name = "openAIChatClient")
    //@Resource(name = "ollamaChatClient")
    private ChatClient chatClient;

    @GetMapping("/genarate")
    public String genarate(@RequestParam(value = "message", defaultValue = "给我讲个笑话") String message) {
        return chatClient
                .prompt()
                .user(message)
                .system(promptSystemSpec -> promptSystemSpec.param("current_date", LocalDate.now().toString()))
                .call()
                .content();
    }

    @GetMapping("/genarateAsStream")
    public Flux<String> genarateAsStream(@RequestParam(value = "message", defaultValue = "给我讲个笑话") String message) {
        Flux<String> stringFlux = chatClient
                .prompt()
                .user(message)
                .system(promptSystemSpec -> promptSystemSpec.param("current_date", LocalDate.now().toString()))
                .stream()
                .content();

        return stringFlux;
    }

}
