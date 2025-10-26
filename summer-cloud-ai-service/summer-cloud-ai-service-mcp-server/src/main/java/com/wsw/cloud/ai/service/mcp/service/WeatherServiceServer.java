package com.wsw.cloud.ai.service.mcp.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

/**
 * @Author wangsongwen
 * @Date 2025/10/20 13:03
 * @Description:
 */
@Service
public class WeatherServiceServer {

    @Tool(name = "getCurrentWeather", description = "查询指定城市的天气")
    public String getCurrentWeather(@ToolParam(description = "城市名称") String city) {
        System.out.println("城市名称：" + city);
        return "天气晴朗";
    }

}
