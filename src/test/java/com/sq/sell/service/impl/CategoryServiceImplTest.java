package com.sq.sell.service.impl;

import com.sq.sell.SellApplicationTests;
import com.sq.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 完成dao层的测试
 */

public class CategoryServiceImplTest extends SellApplicationTests {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        System.out.println(categoryService.findOne(2).getCategoryName());
    }

    @Test
    public void findAll() {
        Assert.assertNotEquals(0,categoryService.findAll().size());
    }

    @Test
    public void findByCategoryTypeIn() {

    }

    @Test
    public void save() {
        ProductCategory pc = categoryService.findOne(3);
        System.out.println(pc.getCategoryName());
        pc.setCategoryType(5);
        System.out.println(categoryService.save(pc).getCategoryType());
    }
}