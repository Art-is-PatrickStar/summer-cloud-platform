package com.wsw.cloud.product.domain.service;

import com.wsw.cloud.product.domain.model.Product;
import com.wsw.cloud.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 产品领域服务
 */
@Service
public class ProductDomainService {

    private final ProductRepository productRepository;

    public ProductDomainService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 创建产品
     */
    public Product createProduct(Product product) {
        // 检查产品编码是否已存在
        if (product.getProductCode() != null && productRepository.existsByProductCode(product.getProductCode())) {
            throw new IllegalStateException("产品编码已存在: " + product.getProductCode());
        }

        // 设置默认状态为下架
        if (product.getStatus() == null) {
            product.setStatus(0);
        }

        return productRepository.save(product);
    }

    /**
     * 更新产品信息
     */
    public Product updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("产品不存在: " + product.getId()));

        // 如果修改了产品编码，需要检查新编码是否已存在
        if (product.getProductCode() != null &&
                !product.getProductCode().equals(existingProduct.getProductCode()) &&
                productRepository.existsByProductCode(product.getProductCode())) {
            throw new IllegalStateException("产品编码已存在: " + product.getProductCode());
        }

        // 更新产品信息
        if (product.getProductName() != null) {
            existingProduct.setProductName(product.getProductName());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }
        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getCategoryId() != null) {
            existingProduct.setCategoryId(product.getCategoryId());
        }
        if (product.getProductCode() != null) {
            existingProduct.setProductCode(product.getProductCode());
        }

        return productRepository.save(existingProduct);
    }

    /**
     * 批量扣减库存
     */
    public void batchReduceStock(List<Long> productIds, List<Integer> quantities) {
        if (productIds.size() != quantities.size()) {
            throw new IllegalArgumentException("产品ID和数量列表长度不一致");
        }

        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            Integer quantity = quantities.get(i);

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalStateException("产品不存在: " + productId));

            product.reduceStock(quantity);
            productRepository.save(product);
        }
    }

}

