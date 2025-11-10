package com.wsw.cloud.ai.service.chat.advisors;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;

import java.util.Map;

/**
 * @Author wangsongwen
 * @Date 2025/11/10 14:06
 * @Description:
 */
public class ReReadingAdvisor implements BaseAdvisor {

    private static final String DEFAULT_USER_TEXT_ADVISE = """
            {re2_input_query}
            重新阅读: {re2_input_query}
            """;

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        // 用户提示词
        String contents = chatClientRequest.prompt().getContents();

        String re2InputQuery = PromptTemplate.builder().template(DEFAULT_USER_TEXT_ADVISE).build().render(Map.of("re2_input_query", contents));

        ChatClientRequest clientRequest = chatClientRequest.mutate().prompt(Prompt.builder().content(re2InputQuery).build()).build();

        return clientRequest;
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        return chatClientResponse;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
