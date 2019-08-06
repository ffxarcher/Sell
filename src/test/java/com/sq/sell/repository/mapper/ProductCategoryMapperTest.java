package com.sq.sell.repository.mapper;

import com.sq.sell.SellApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class ProductCategoryMapperTest extends SellApplicationTests {
    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void findById()
    {
        log.info(mapper.findById(1).getCategoryName());
    }

    @Test
    public void findByName()
    {
        log.info(mapper.findByName("好吃的").getCreateTime()+" ");
    }

}