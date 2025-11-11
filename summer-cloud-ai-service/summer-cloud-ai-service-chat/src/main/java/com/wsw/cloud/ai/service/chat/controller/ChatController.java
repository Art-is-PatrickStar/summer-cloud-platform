package com.wsw.cloud.ai.service.chat.controller;

import com.alibaba.fastjson2.JSON;
import com.wsw.cloud.ai.service.chat.dto.Address;
import com.wsw.cloud.ai.service.chat.dto.ChatDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

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

    @Resource
    private VectorStore vectorStore;

    @Value("classpath:/file/茅台公告.pdf")
    private org.springframework.core.io.Resource resource;

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

    @GetMapping("/testEntityList")
    public List<Address> testEntityList(@RequestParam(value = "message", defaultValue = "随机生成五份信息") String message) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<Address>>() {
                });
    }

    @GetMapping("/testVectorStoreSearch")
    public String testVectorStoreSearch() {
//        List<Document> documents = List.of(
//                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
//                new Document("The World is Big and Salvation Lurks Around the Corner"),
//                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));
//
//        // Add the documents to Elasticsearch
//        vectorStore.add(documents);

        // Retrieve documents similar to a query
        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

        log.info("results: {}", JSON.toJSONString(results));

        return "ok";
    }

    @GetMapping("/loadDocument")
    public String loadDocument() {
        // 读取
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(this.resource);
        List<Document> documentList = tikaDocumentReader.read();
        for (Document document : documentList) {
            log.info("document: {}", document.getText());
        }

        // 分割
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
        List<Document> splitDocumentList = tokenTextSplitter.apply(documentList);
        for (Document document : splitDocumentList) {
            log.info("splitDocument: {}", document.getText());
        }

        // 存储
        vectorStore.add(splitDocumentList);

        return "ok";
    }

}
