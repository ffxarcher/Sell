package com.sq.sell.service.impl;

import com.sq.sell.entity.ProductInfo;
import com.sq.sell.enums.ProductStatusEnum;
import com.sq.sell.repository.ProductRepository;
import com.sq.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductInfo findOne(String productId) {
        return productRepository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productRepository.save(productInfo);
    }
}
