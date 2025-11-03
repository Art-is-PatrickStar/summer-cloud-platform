package com.wsw.cloud.product.application.query;

import lombok.Data;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 产品查询对象
 */
@Data
public class ProductQuery {

    private Long id;

    private String productCode;

    private String productName;

    private Long categoryId;

    private Integer status;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

}

