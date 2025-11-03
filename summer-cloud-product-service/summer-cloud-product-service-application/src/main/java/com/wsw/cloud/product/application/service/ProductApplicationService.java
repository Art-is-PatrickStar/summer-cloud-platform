package com.wsw.cloud.product.application.service;

import com.wsw.cloud.product.application.command.CreateProductCommand;
import com.wsw.cloud.product.application.command.ReduceStockCommand;
import com.wsw.cloud.product.application.command.UpdateProductCommand;
import com.wsw.cloud.product.application.converter.ProductConverter;
import com.wsw.cloud.product.application.dto.ProductDTO;
import com.wsw.cloud.product.domain.model.Product;
import com.wsw.cloud.product.domain.model.value.Money;
import com.wsw.cloud.product.domain.repository.ProductRepository;
import com.wsw.cloud.product.domain.repository.pagination.PageResult;
import com.wsw.cloud.product.domain.service.ProductDomainService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author wangsongwen
 * @Date 2025/10/26 23:34
 * @Description: 产品应用服务
 */
@Service
public class ProductApplicationService {

    @Resource
    private ProductRepository productRepository;
    @Resource
    private ProductDomainService productDomainService;

    /**
     * 创建产品
     */
    @Transactional(rollbackFor = Exception.class)
    public ProductDTO createProduct(CreateProductCommand command) {
        Product product = Product.builder()
                .productCode(command.getProductCode())
                .productName(command.getProductName())
                .description(command.getDescription())
                .price(command.getPrice() != null ? Money.ofCents(command.getPrice()) : null)
                .stock(command.getStock() != null ? command.getStock() : 0)
                .categoryId(command.getCategoryId())
                .status(0)
                .build();

        Product savedProduct = productDomainService.createProduct(product);
        return ProductConverter.toDTO(savedProduct);
    }

    /**
     * 更新产品
     */
    @Transactional(rollbackFor = Exception.class)
    public ProductDTO updateProduct(UpdateProductCommand command) {
        Product product = Product.builder()
                .id(command.getId())
                .productCode(command.getProductCode())
                .productName(command.getProductName())
                .description(command.getDescription())
                .price(command.getPrice() != null ? Money.ofCents(command.getPrice()) : null)
                .stock(command.getStock())
                .categoryId(command.getCategoryId())
                .build();

        Product updatedProduct = productDomainService.updateProduct(product);
        Product savedProduct = productRepository.save(updatedProduct);
        return ProductConverter.toDTO(savedProduct);
    }

    /**
     * 根据ID查询产品
     */
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("产品不存在: " + id));
        return ProductConverter.toDTO(product);
    }

    /**
     * 根据产品编码查询产品
     */
    public ProductDTO getProductByCode(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new IllegalStateException("产品不存在: " + productCode));
        return ProductConverter.toDTO(product);
    }

    /**
     * 查询所有产品
     */
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ProductConverter.toDTOList(products);
    }

    /**
     * 根据分类ID查询产品列表
     */
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return ProductConverter.toDTOList(products);
    }

    /**
     * 根据状态查询产品列表
     */
    public List<ProductDTO> getProductsByStatus(Integer status) {
        List<Product> products = productRepository.findByStatus(status);
        return ProductConverter.toDTOList(products);
    }

    /**
     * 分页查询
     */
    public PageResult<ProductDTO> page(String productCode, String productName, Long categoryId, Integer status, int pageNum, int pageSize) {
        PageResult<Product> page = productRepository.page(productCode, productName, categoryId, status, pageNum, pageSize);
        List<ProductDTO> records = ProductConverter.toDTOList(page.getRecords());
        return new PageResult<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), records);
    }

    /**
     * 扣减库存
     */
    @Transactional(rollbackFor = Exception.class)
    public void reduceStock(ReduceStockCommand command) {
        Product product = productRepository.findById(command.getProductId())
                .orElseThrow(() -> new IllegalStateException("产品不存在: " + command.getProductId()));

        product.reduceStock(command.getQuantity());
        productRepository.save(product);
    }

    /**
     * 上架产品
     */
    @Transactional(rollbackFor = Exception.class)
    public void onShelf(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("产品不存在: " + id));

        product.onShelf();
        productRepository.save(product);
    }

    /**
     * 下架产品
     */
    @Transactional(rollbackFor = Exception.class)
    public void offShelf(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("产品不存在: " + id));

        product.offShelf();
        productRepository.save(product);
    }

    /**
     * 删除产品
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(Long id) {
        if (!productRepository.findById(id).isPresent()) {
            throw new IllegalStateException("产品不存在: " + id);
        }
        productRepository.deleteById(id);
    }

}

