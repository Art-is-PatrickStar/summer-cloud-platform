package com.wsw.cloud.product.interfaces.controller;

import com.wsw.cloud.product.application.command.CreateProductCommand;
import com.wsw.cloud.product.application.command.ReduceStockCommand;
import com.wsw.cloud.product.application.command.UpdateProductCommand;
import com.wsw.cloud.product.application.dto.ProductDTO;
import com.wsw.cloud.product.application.service.ProductApplicationService;
import com.wsw.cloud.product.domain.repository.pagination.PageResult;
import com.wsw.cloud.product.interfaces.response.ApiResponse;
import com.wsw.cloud.product.interfaces.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 产品服务接口
 */
@Slf4j
@RestController
@RequestMapping("/api/products")
@Tag(name = "ProductController", description = "产品服务接口")
public class ProductController {

    @Resource
    private ProductApplicationService productApplicationService;

    @Operation(summary = "创建产品")
    @Parameters({
            @Parameter(name = "command", description = "创建产品参数", required = true)
    })
    @PostMapping("/createProduct")
    public ApiResponse<ProductDTO> createProduct(@Validated @RequestBody CreateProductCommand command) {
        ProductDTO product = productApplicationService.createProduct(command);
        return ApiResponse.ok(product);
    }

    @Operation(summary = "更新产品")
    @Parameters({
            @Parameter(name = "command", description = "更新产品参数", required = true)
    })
    @PostMapping("/updateProduct")
    public ApiResponse<ProductDTO> updateProduct(@Validated @RequestBody UpdateProductCommand command) {
        ProductDTO product = productApplicationService.updateProduct(command);
        return ApiResponse.ok(product);
    }

    @Operation(summary = "根据ID查询产品")
    @Parameters({
            @Parameter(name = "id", description = "产品ID", required = true)
    })
    @GetMapping("/getProductById/{id}")
    public ApiResponse<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productApplicationService.getProductById(id);
        return ApiResponse.ok(product);
    }

    @Operation(summary = "根据产品编码查询产品")
    @Parameters({
            @Parameter(name = "productCode", description = "产品编码", required = true)
    })
    @GetMapping("/getProductByCode/{productCode}")
    public ApiResponse<ProductDTO> getProductByCode(@PathVariable String productCode) {
        ProductDTO product = productApplicationService.getProductByCode(productCode);
        return ApiResponse.ok(product);
    }

    @Operation(summary = "查询所有产品")
    @GetMapping("/getAllProducts")
    public ApiResponse<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productApplicationService.getAllProducts();
        return ApiResponse.ok(products);
    }

    @Operation(summary = "根据分类ID查询产品列表")
    @Parameters({
            @Parameter(name = "categoryId", description = "分类ID", required = true)
    })
    @GetMapping("/getProductsByCategoryId/{categoryId}")
    public ApiResponse<List<ProductDTO>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<ProductDTO> products = productApplicationService.getProductsByCategoryId(categoryId);
        return ApiResponse.ok(products);
    }

    @Operation(summary = "根据状态查询产品列表")
    @Parameters({
            @Parameter(name = "status", description = "状态", required = true)
    })
    @GetMapping("/getProductsByStatus/{status}")
    public ApiResponse<List<ProductDTO>> getProductsByStatus(@PathVariable Integer status) {
        List<ProductDTO> products = productApplicationService.getProductsByStatus(status);
        return ApiResponse.ok(products);
    }

    @Operation(summary = "扣减库存")
    @Parameters({
            @Parameter(name = "command", description = "扣减库存参数", required = true)
    })
    @PostMapping("/reduce-stock")
    public ApiResponse<Void> reduceStock(@Validated @RequestBody ReduceStockCommand command) {
        productApplicationService.reduceStock(command);
        return ApiResponse.ok(null);
    }

    @Operation(summary = "上架产品")
    @Parameters({
            @Parameter(name = "id", description = "产品ID", required = true)
    })
    @PostMapping("/onShelf/{id}")
    public ApiResponse<Void> onShelf(@PathVariable Long id) {
        productApplicationService.onShelf(id);
        return ApiResponse.ok(null);
    }

    @Operation(summary = "下架产品")
    @Parameters({
            @Parameter(name = "id", description = "产品ID", required = true)
    })
    @PostMapping("/offShelf/{id}")
    public ApiResponse<Void> offShelf(@PathVariable Long id) {
        productApplicationService.offShelf(id);
        return ApiResponse.ok(null);
    }

    @Operation(summary = "删除产品")
    @Parameters({
            @Parameter(name = "id", description = "产品ID", required = true)
    })
    @GetMapping("/deleteProduct/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        productApplicationService.deleteProduct(id);
        return ApiResponse.ok(null);
    }

    @Operation(summary = "分页查询产品")
    @GetMapping("/page")
    public PageResponse<ProductDTO> page(
            @RequestParam(name = "productCode", required = false) String productCode,
            @RequestParam(name = "productName", required = false) String productName,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        PageResult<ProductDTO> page = productApplicationService.page(productCode, productName, categoryId, status, pageNum, pageSize);
        return PageResponse.of(page.getRecords(), page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize());
    }

}

