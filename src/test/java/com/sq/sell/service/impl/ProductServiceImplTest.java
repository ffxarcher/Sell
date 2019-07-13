package com.sq.sell.service.impl;

import com.sq.sell.SellApplicationTests;
import com.sq.sell.entity.ProductInfo;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ProductServiceImplTest extends SellApplicationTests {

    @Autowired
    private  ProductServiceImpl productService;
    @Test
    public void findOne() {
        System.out.println(productService.findOne("157875196366160022"));
    }

    @Test
    public void findUpAll() {
        System.out.println(productService.findUpAll());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> productInfos = productService.findAll(pageRequest);
       // System.out.println(productInfos.get().map(ProductInfo::getProductName).collect(Collectors.toList()));
        System.out.println(productInfos.get().map(e -> new String(e.getProductName())).collect(Collectors.toList()));
    }

    @Test
    public void save() {
    }
}