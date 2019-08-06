package com.sq.sell.service.impl;

import com.sq.sell.SellApplicationTests;
import com.sq.sell.dto.OrderDTO;
import com.sq.sell.service.OrderService;
import com.sq.sell.service.PushMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class PushMessageImplTest extends SellApplicationTests {

    @Autowired
    private  OrderService orderService;
    @Autowired
    private PushMessage pushMessage;

    @Test
    public void orderStatus() {
        OrderDTO orderDTO = orderService.findOne("1563550122377558068");
        pushMessage.orderStatus(orderDTO);
    }
}