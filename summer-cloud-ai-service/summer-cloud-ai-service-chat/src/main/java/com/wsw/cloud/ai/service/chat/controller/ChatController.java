package com.wsw.cloud.ai.service.chat.controller;

import com.alibaba.fastjson2.JSON;
import com.wsw.cloud.ai.service.chat.dto.Address;
import com.wsw.cloud.ai.service.chat.dto.ChatDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

/**
 * @Author wangsongwen
 * @Date 2025/10/19 21:39
 * @Description:
 */
@Slf4j
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

    // produces属性：指定响应的Content-Type和字符集，解析默认的流结果的乱码
    //@PostMapping(value = "/genarateAsStream", produces = "text/event-stream;charset=UTF-8")
    @PostMapping(value = "/genarateAsStream", produces = "text/stream;charset=UTF-8")
    public Flux<String> genarateAsStream(@RequestBody ChatDTO chatDTO) {
        Flux<String> stringFlux = chatClient
                .prompt()
                .user(chatDTO.getMessage())
                .system(promptSystemSpec -> promptSystemSpec.param("current_date", LocalDate.now().toString()))
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, chatDTO.getConversationId()))
                .stream()
                .content();

        //stringFlux.toIterable().forEach(System.out::println);

        return stringFlux;
    }

    @GetMapping("/testBoolean")
    public void testBoolean(@RequestParam(value = "message") String message) {
        Boolean b = chatClient
                .prompt()
                .user(message)
                .system("""
                        请判断用户信息是否表达了投诉意图？
                        只能用 true 或 false 回答，不要输出多余内容
                        """)
                .call()
                .entity(Boolean.class);

        if (Boolean.TRUE.equals(b)) {
            log.info("用户是投诉，转人工客服！");
        } else {
            log.info("用户不是投诉，自动流转客服机器人！");
            // 继续调用客服ChatClient进行对话
        }
    }

    @GetMapping("/testEntity")
    public Address testEntity(@RequestParam(value = "message") String message) {
        Address address = chatClient
                .prompt()
                .user(message)
                .system("""
                        请从文本中提取收货信息
                        """)
                .call()
                .entity(Address.class);

        log.info("收货信息：{}", JSON.toJSONString(address));

        return address;
    }

}
