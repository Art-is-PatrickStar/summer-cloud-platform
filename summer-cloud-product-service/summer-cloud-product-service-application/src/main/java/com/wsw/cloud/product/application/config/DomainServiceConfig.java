package com.wsw.cloud.product.application.config;

import com.wsw.cloud.product.domain.repository.ProductRepository;
import com.wsw.cloud.product.domain.service.ProductDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfig {

    @Bean
    public ProductDomainService productDomainService(ProductRepository productRepository) {
        return new ProductDomainService(productRepository);
    }
}


