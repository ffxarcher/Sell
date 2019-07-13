package com.sq.sell.repository;

import com.sq.sell.SellApplicationTests;
import com.sq.sell.entity.ProductCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;


public class ProductCategoryRepositoryTest extends SellApplicationTests {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void productCategoryRepositoryTest()
    {
        System.out.println(productCategoryRepository.findAll().size());
    }

    @Test
    @Ignore
    public void saveTest()
    {
        ProductCategory productCategory = new ProductCategory();

        productCategory.setCategoryName("测试");
        productCategory.setCategoryType(3);
        productCategory.setCreateTime(new Date());
        productCategory.setUpdateTime(new Date());

        productCategoryRepository.save(productCategory);
    }


    @Test
    @Ignore
    public void updateTest()
    {
        ProductCategory productCategory = new ProductCategory();

        productCategory.setCategoryId(3);
        productCategory.setCategoryName("测试更新");
        productCategory.setCategoryType(21);
        productCategory.setCreateTime(new Date());
        productCategory.setUpdateTime(new Date());
       // System.out.println(new Date());

        productCategoryRepository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest()
    {
        System.out.println(
                productCategoryRepository.findByCategoryTypeIn(Arrays.asList(1,2,3)).size()
        );
    }

}