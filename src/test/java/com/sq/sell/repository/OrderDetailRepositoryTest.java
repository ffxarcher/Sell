package com.sq.sell.repository;

import com.sq.sell.SellApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class OrderDetailRepositoryTest extends SellApplicationTests {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public  void orderDetailRepositoryTest()
    {
        System.out.println(orderDetailRepository.findByOrderId("123456"));
    }

}