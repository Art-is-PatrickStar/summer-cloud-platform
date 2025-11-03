package com.wsw.cloud.product.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsw.cloud.product.infrastructure.persistence.entity.ProductDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 产品数据库Mapper
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductDO> {

}
