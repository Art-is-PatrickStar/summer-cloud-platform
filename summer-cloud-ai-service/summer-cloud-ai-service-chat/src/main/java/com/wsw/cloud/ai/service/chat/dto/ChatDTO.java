package com.wsw.cloud.ai.service.chat.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author wangsongwen
 * @Date 2025/11/10 12:05
 * @Description:
 */
@Data
public class ChatDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4516313684015731197L;

    private String message;

    private String conversationId;

}
