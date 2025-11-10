package com.wsw.cloud.ai.service.chat.dto;

import lombok.Data;

/**
 * @Author wangsongwen
 * @Date 2025/11/10 15:41
 * @Description:
 */
@Data
public class Address {
    // 收件人姓名
    private String name;
    // 联系电话
    private String phone;
    // 省
    private String province;
    // 市
    private String city;
    // 区/县
    private String district;
    // 详细地址
    private String detail;
}
