package com.wsw.cloud.product.domain.model;

import com.wsw.cloud.product.domain.model.value.Money;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 产品聚合根 包含业务逻辑（库存管理、上架/下架）
 */
@Data
@Builder
public class Product {

    /**
     * 产品ID
     */
    private Long id;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品描述
     */
    private String description;

    /**
     * 价格（值对象，以分为单位）
     */
    private Money price;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 状态：0-下架，1-上架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 减少库存
     */
    public void reduceStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("扣减数量必须大于0");
        }
        if (this.stock == null || this.stock < quantity) {
            throw new IllegalStateException("库存不足");
        }
        this.stock = this.stock - quantity;
    }

    /**
     * 增加库存
     */
    public void increaseStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("增加数量必须大于0");
        }
        if (this.stock == null) {
            this.stock = 0;
        }
        this.stock = this.stock + quantity;
    }

    /**
     * 检查是否可以上架
     */
    public boolean canOnShelf() {
        return this.stock != null && this.stock > 0 && this.price != null && this.price.isPositive();
    }

    /**
     * 上架
     */
    public void onShelf() {
        if (!canOnShelf()) {
            throw new IllegalStateException("产品不满足上架条件");
        }
        this.status = 1;
    }

    /**
     * 下架
     */
    public void offShelf() {
        this.status = 0;
    }

}

