package com.wsw.cloud.product.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wangsongwen
 * @Date 2025/11/3 22:31
 * @Description:
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI restOpenApi() {
        return new OpenAPI().info(new Info()
                .title("summer-cloud-product-service")
                .description("产商品服务")
                .version("1.0.0"));
    }

}
