package com.sq.sell.repository;

import com.sq.sell.SellApplicationTests;
import com.sq.sell.entity.OrderMaster;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class OrderMasterRepositoryTest extends SellApplicationTests {
    @Autowired
    private  OrderMasterRepository orderMasterRepository;

    @Test
    public void orderMasterRepositoryTest()
    {
        PageRequest pageRequest = new PageRequest(0,1);

        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid("1101110",pageRequest);

        System.out.println(orderMasters.get().map(OrderMaster::getBuyerName).collect(Collectors.toList()));
    }

}