package com.wsw.cloud.product.application.converter;

import com.wsw.cloud.product.application.dto.ProductDTO;
import com.wsw.cloud.product.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

public final class ProductConverter {

    private ProductConverter() {}

    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setProductCode(product.getProductCode());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice() != null ? product.getPrice().getAmountInCents() : null);
        dto.setStock(product.getStock());
        dto.setCategoryId(product.getCategoryId());
        dto.setStatus(product.getStatus());
        dto.setCreateTime(product.getCreateTime());
        dto.setUpdateTime(product.getUpdateTime());
        return dto;
    }

    public static List<ProductDTO> toDTOList(List<Product> products) {
        if (products == null) {
            return List.of();
        }
        List<ProductDTO> list = new ArrayList<>(products.size());
        for (Product p : products) {
            list.add(toDTO(p));
        }
        return list;
    }
}

