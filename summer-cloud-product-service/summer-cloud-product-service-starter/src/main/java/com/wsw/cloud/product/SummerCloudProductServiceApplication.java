package com.wsw.cloud.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description:
 */
@SpringBootApplication(scanBasePackages = "com.wsw.cloud")
public class SummerCloudProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SummerCloudProductServiceApplication.class, args);
    }

}