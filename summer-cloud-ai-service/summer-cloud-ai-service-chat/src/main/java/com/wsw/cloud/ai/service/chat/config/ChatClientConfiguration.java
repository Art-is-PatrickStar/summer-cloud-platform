package com.wsw.cloud.ai.service.chat.config;

import com.wsw.cloud.ai.service.chat.tools.TradeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            1. 在调用tools之前需等用户确认, 用户确认完成之后再执行
            2. 请用中文回复
            
            今天的日期是: {current_date}
            """;

    @Bean
    public ChatClient openAIChatClient(OpenAiChatModel openAiChatModel,
                                       ChatMemory chatMemory,
                                       TradeTools tradeTools,
                                       ToolCallbackProvider toolCallbackProvider) {
        return ChatClient
                .builder(openAiChatModel)
                .defaultSystem(DEFAULT_SYSTEM_PROMPT)
                .defaultAdvisors(PromptChatMemoryAdvisor.builder(chatMemory).build())
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultTools(tradeTools)
                .build();
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel,
                                       ChatMemory chatMemory,
                                       TradeTools tradeTools,
                                       ToolCallbackProvider toolCallbackProvider) {
        return ChatClient
                .builder(ollamaChatModel)
                .defaultSystem(DEFAULT_SYSTEM_PROMPT)
                .defaultAdvisors(PromptChatMemoryAdvisor.builder(chatMemory).build())
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultTools(tradeTools)
                .build();
    }

}
