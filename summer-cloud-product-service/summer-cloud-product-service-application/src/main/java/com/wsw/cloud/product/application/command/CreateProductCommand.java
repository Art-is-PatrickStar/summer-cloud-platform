package com.wsw.cloud.product.application.command;

import lombok.Data;
import jakarta.validation.constraints.*;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 创建产品命令
 */
@Data
public class CreateProductCommand {

    @NotBlank
    private String productCode;

    @NotBlank
    private String productName;

    private String description;

    @NotNull
    @PositiveOrZero
    private Integer price;

    @NotNull
    @PositiveOrZero
    private Integer stock;

    private Long categoryId;

}

