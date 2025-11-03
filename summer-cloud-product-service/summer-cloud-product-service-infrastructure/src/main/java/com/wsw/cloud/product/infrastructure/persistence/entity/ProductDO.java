package com.wsw.cloud.product.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 产品数据库实体
 */
@Data
@TableName("product")
public class ProductDO {

    @TableId(type = IdType.INPUT)
    private Long id;

    private String productCode;

    private String productName;

    private String description;

    private Integer price;

    private Integer stock;

    private Long categoryId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}

