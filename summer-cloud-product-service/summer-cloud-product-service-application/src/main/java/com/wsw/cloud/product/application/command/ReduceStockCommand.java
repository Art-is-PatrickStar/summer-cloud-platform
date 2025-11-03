package com.wsw.cloud.product.application.command;

import lombok.Data;
import jakarta.validation.constraints.*;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 扣减库存命令
 */
@Data
public class ReduceStockCommand {

    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;

}

