package com.wsw.cloud.ai.service.chat.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * @Author wangsongwen
 * @Date 2025/10/20 00:50
 * @Description:
 */
@Component
public class TradeTools {

    @Tool(name = "cancelTrade", description = "取消交易")
    public String cancelTrade(@ToolParam(description = "订单ID") String tradeId,
                              @ToolParam(description = "商品名称") String commodityName) {
        return "取消交易成功";
    }

}
