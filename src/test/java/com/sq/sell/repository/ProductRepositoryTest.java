package com.sq.sell.repository;

import com.sq.sell.SellApplicationTests;
import com.sq.sell.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.Date;



public class ProductRepositoryTest extends SellApplicationTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void productRepositoryTest()
    {
        Assert.assertNotEquals(0, productRepository.findByProductStatus(0).size());
    }

    /**
     * save和delete均完成测试
     */
    @Test
    public void saveTest()
    {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setCategoryType(1);
        productInfo.setProductStatus(0);
        productInfo.setProductDescription("test");
        productInfo.setCreateTime(new Date());
        productInfo.setProductPrice(BigDecimal.valueOf(3.6));
        productInfo.setUpdateTime(new Date());
        productInfo.setProductStock(12);
        productInfo.setProductName("测试使用");
        productRepository.save(productInfo);
        System.out.println(productRepository.findById("123").get().getProductName());
        productRepository.deleteById("123");
    }


}