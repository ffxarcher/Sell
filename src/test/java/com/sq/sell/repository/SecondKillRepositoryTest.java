package com.sq.sell.repository;

import com.sq.sell.SellApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class SecondKillRepositoryTest extends SellApplicationTests {

    @Autowired
    private  SecondKillRepository secondKillRepository;

    @Test
    public void test()
    {
        System.out.println(secondKillRepository.findByProductId("123456"));
    }

}