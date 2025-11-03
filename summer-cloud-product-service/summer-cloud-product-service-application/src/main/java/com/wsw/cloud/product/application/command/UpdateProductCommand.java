package com.wsw.cloud.product.application.command;

import lombok.Data;
import jakarta.validation.constraints.*;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 更新产品命令
 */
@Data
public class UpdateProductCommand {

    @NotNull
    private Long id;

    private String productCode;

    private String productName;

    private String description;

    @PositiveOrZero
    private Integer price;

    @PositiveOrZero
    private Integer stock;

    private Long categoryId;

}

