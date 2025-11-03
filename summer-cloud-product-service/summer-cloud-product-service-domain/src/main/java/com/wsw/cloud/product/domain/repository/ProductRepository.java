package com.wsw.cloud.product.domain.repository;

import com.wsw.cloud.product.domain.aggregate.Product;
import com.wsw.cloud.product.domain.repository.pagination.PageResult;

import java.util.List;
import java.util.Optional;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 产品仓储接口（领域层）
 */
public interface ProductRepository {

    /**
     * 保存产品
     */
    Product save(Product product);

    /**
     * 根据ID查找产品
     */
    Optional<Product> findById(Long id);

    /**
     * 根据产品编码查找产品
     */
    Optional<Product> findByProductCode(String productCode);

    /**
     * 根据分类ID查找产品列表
     */
    List<Product> findByCategoryId(Long categoryId);

    /**
     * 查找所有产品
     */
    List<Product> findAll();

    /**
     * 根据状态查找产品列表
     */
    List<Product> findByStatus(Integer status);

    /**
     * 删除产品
     */
    void deleteById(Long id);

    /**
     * 检查产品编码是否存在
     */
    boolean existsByProductCode(String productCode);

    /**
     * 条件分页查询
     */
    PageResult<Product> page(String productCode, String productName, Long categoryId, Integer status, int pageNum, int pageSize);

}

