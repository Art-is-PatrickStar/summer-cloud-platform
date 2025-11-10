package com.wsw.cloud.ai.service.chat.config;

import com.wsw.cloud.ai.service.chat.advisors.ReReadingAdvisor;
import com.wsw.cloud.ai.service.chat.tools.TradeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author wangsongwen
 * @Date 2025/10/19 23:55
 * @Description:
 */
@Configuration
public class ChatClientConfiguration {

    private static final String DEFAULT_SYSTEM_PROMPT = """
            ## 角色
            你是一个个人助理.
            
            ## 要求
            1. 在调用tools之前（除查询操作外）需等用户确认, 用户确认完成之后再执行
            2. 请用中文回复
            
            今天的日期是: {current_date}
            """;

    @Bean
    public ChatClient openAIChatClient(OpenAiChatModel openAiChatModel,
                                       ChatMemory chatMemory,
                                       TradeTools tradeTools,
                                       ToolCallbackProvider toolCallbackProvider) {
        return ChatClient.builder(openAiChatModel)
                // 系统提示词
                .defaultSystem(DEFAULT_SYSTEM_PROMPT)
                // 拦截器
                .defaultAdvisors(
                        // 聊天记录持久化
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        // 日志打印
                        new SimpleLoggerAdvisor(),
                        // 敏感词
                        new SafeGuardAdvisor(List.of("将军"))
                        // 自定义拦截器
                        //new ReReadingAdvisor()
                        // rag检索
                )
                // mcp
                .defaultToolCallbacks(toolCallbackProvider)
                // 工具
                .defaultTools(tradeTools)
                .build();
    }

//    @Bean
//    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel,
//                                       ChatMemory chatMemory,
//                                       TradeTools tradeTools,
//                                       ToolCallbackProvider toolCallbackProvider) {
//        return ChatClient.builder(ollamaChatModel)
//                .defaultSystem(DEFAULT_SYSTEM_PROMPT)
//                .defaultAdvisors(PromptChatMemoryAdvisor.builder(chatMemory).build(), new SimpleLoggerAdvisor(), new SafeGuardAdvisor(List.of("王松文")))
//                .defaultToolCallbacks(toolCallbackProvider)
//                .defaultTools(tradeTools)
//                .build();
//    }

    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        // InMemoryChatMemoryRepository 内存存储

        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(10)
                .build();
    }

}
