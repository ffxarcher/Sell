package com.sq.sell.repository;

import com.sq.sell.SellApplicationTests;
import com.sq.sell.entity.SellerInfo;
import com.sq.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class SellerInfoRepositoryTest extends SellApplicationTests {

    @Autowired
    private SellerInfoRepository repository;
    @Test
    public void save()
    {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setOpenid("oxsXxvn-ZjSLIRXOnTREY7A5iYK0");
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfo.setSellerName("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setCreateTime(new Date());
        sellerInfo.setUpdateTime(new Date());
        //Assert.assertEquals(1,repository.save(sellerInfo));

    }

    @Test
    public void findByOpenid()
    {
        Assert.assertEquals("oxsXxvn-ZjSLIRXOnTREY7A5iYK0",repository.findByOpenid("oxsXxvn-ZjSLIRXOnTREY7A5iYK0").getOpenid());
    }

    @Test
    public void test()
    {
        System.out.println(repository.findBySellerNameAndPassword("admin","admin").getOpenid());
    }

}