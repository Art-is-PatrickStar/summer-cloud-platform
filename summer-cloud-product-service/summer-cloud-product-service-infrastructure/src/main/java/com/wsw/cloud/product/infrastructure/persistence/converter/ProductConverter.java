package com.wsw.cloud.product.infrastructure.persistence.converter;

import com.wsw.cloud.product.domain.aggregate.Product;
import com.wsw.cloud.product.domain.valueobject.Money;
import com.wsw.cloud.product.infrastructure.persistence.entity.ProductDO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 产品实体转换器 领域模型与数据库实体转换
 */
public class ProductConverter {
    
    public static ProductDO toDO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDO productDO = new ProductDO();
        BeanUtils.copyProperties(product, productDO);
        // Money -> int cents
        if (product.getPrice() != null) {
            productDO.setPrice(product.getPrice().getAmountInCents());
        }
        return productDO;
    }
    
    public static Product toDomain(ProductDO productDO) {
        if (productDO == null) {
            return null;
        }
        Product product = Product.builder().build();
        BeanUtils.copyProperties(productDO, product);
        // int cents -> Money
        if (productDO.getPrice() != null) {
            product.setPrice(Money.ofCents(productDO.getPrice()));
        }
        return product;
    }
    
    public static List<Product> toDomainList(List<ProductDO> productDOList) {
        if (productDOList == null) {
            return null;
        }
        return productDOList.stream()
                .map(ProductConverter::toDomain)
                .collect(Collectors.toList());
    }
}

