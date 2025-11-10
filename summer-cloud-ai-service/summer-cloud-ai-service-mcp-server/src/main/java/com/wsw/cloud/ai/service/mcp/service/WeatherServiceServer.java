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

    @Tool(name = "getCurrentWeather", description = "获取指定位置天气，如果只有位置信息则根据位置自动推算经纬度")
    public String getCurrentWeather(@ToolParam(description = "经度") double longitude,
                                    @ToolParam(description = "纬度") double latitude) {
        return "天气晴朗";
    }

}
