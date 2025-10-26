package com.wsw.cloud.ai.service.mcp.config;

import com.wsw.cloud.ai.service.mcp.service.WeatherServiceServer;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wangsongwen
 * @Date 2025/10/20 13:07
 * @Description:
 */
@Configuration
public class McpServerConfiguration {

    @Bean
    public ToolCallbackProvider toolCallbackProvider(WeatherServiceServer weatherServiceServer) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherServiceServer)
                .build();
    }

}
