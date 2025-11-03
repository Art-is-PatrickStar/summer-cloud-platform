--库
create schema `summer-cloud-platform` collate utf8mb4_general_ci;

-- 产品表
CREATE TABLE IF NOT EXISTS `product` (
    `id` BIGINT NOT NULL COMMENT '产品ID',
    `product_code` VARCHAR(64) NOT NULL COMMENT '产品编码',
    `product_name` VARCHAR(128) NOT NULL COMMENT '产品名称',
    `description` TEXT COMMENT '产品描述',
    `price` INT NOT NULL COMMENT '价格(分)',
    `stock` INT NOT NULL DEFAULT 0 COMMENT '库存数量',
    `category_id` BIGINT COMMENT '分类ID',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-已下架，1-已上架',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_product_code` (`product_code`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品表';


