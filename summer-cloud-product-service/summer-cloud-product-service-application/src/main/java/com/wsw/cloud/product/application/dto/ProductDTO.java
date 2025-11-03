package com.wsw.cloud.product.application.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 产品DTO
 */
@Data
public class ProductDTO {

    private Long id;

    private String productCode;

    private String productName;

    private String description;

    private Integer price;

    private Integer stock;

    private Long categoryId;

    private String categoryName;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}

