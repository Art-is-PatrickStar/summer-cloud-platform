package com.wsw.cloud.product.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsw.cloud.product.domain.aggregate.Product;
import com.wsw.cloud.product.domain.repository.ProductRepository;
import com.wsw.cloud.product.domain.repository.pagination.PageResult;
import com.wsw.cloud.product.infrastructure.persistence.converter.ProductConverter;
import com.wsw.cloud.product.infrastructure.persistence.entity.ProductDO;
import com.wsw.cloud.product.infrastructure.persistence.mapper.ProductMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 产品仓储实现
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Resource
    private ProductMapper productMapper;

    @Override
    public Product save(Product product) {
        ProductDO productDO = ProductConverter.toDO(product);
        if (product.getId() == null) {
            productDO.setId(System.currentTimeMillis());
            productMapper.insert(productDO);
            product.setId(productDO.getId());
        } else {
            productMapper.updateById(productDO);
        }
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        ProductDO productDO = productMapper.selectById(id);
        return Optional.ofNullable(ProductConverter.toDomain(productDO));
    }

    @Override
    public Optional<Product> findByProductCode(String productCode) {
        LambdaQueryWrapper<ProductDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductDO::getProductCode, productCode);
        ProductDO productDO = productMapper.selectOne(wrapper);
        return Optional.ofNullable(ProductConverter.toDomain(productDO));
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        LambdaQueryWrapper<ProductDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductDO::getCategoryId, categoryId);
        List<ProductDO> productDOList = productMapper.selectList(wrapper);
        return ProductConverter.toDomainList(productDOList);
    }

    @Override
    public List<Product> findAll() {
        List<ProductDO> productDOList = productMapper.selectList(null);
        return ProductConverter.toDomainList(productDOList);
    }

    @Override
    public List<Product> findByStatus(Integer status) {
        LambdaQueryWrapper<ProductDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductDO::getStatus, status);
        List<ProductDO> productDOList = productMapper.selectList(wrapper);
        return ProductConverter.toDomainList(productDOList);
    }

    @Override
    public void deleteById(Long id) {
        productMapper.deleteById(id);
    }

    @Override
    public boolean existsByProductCode(String productCode) {
        LambdaQueryWrapper<ProductDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductDO::getProductCode, productCode);
        return productMapper.selectCount(wrapper) > 0;
    }

    @Override
    public PageResult<Product> page(String productCode, String productName, Long categoryId, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<ProductDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(productCode != null && !productCode.isEmpty(), ProductDO::getProductCode, productCode)
                .like(productName != null && !productName.isEmpty(), ProductDO::getProductName, productName)
                .eq(categoryId != null, ProductDO::getCategoryId, categoryId)
                .eq(status != null, ProductDO::getStatus, status);

        Page<ProductDO> page = new Page<>(pageNum, pageSize);
        Page<ProductDO> result = productMapper.selectPage(page, wrapper);
        return new PageResult<>(result.getTotal(), result.getPages(), (int) result.getCurrent(), (int) result.getSize(),
                ProductConverter.toDomainList(result.getRecords()));
    }

}

