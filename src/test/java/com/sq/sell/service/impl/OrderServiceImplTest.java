package com.sq.sell.service.impl;

import com.sq.sell.SellApplicationTests;
import com.sq.sell.dto.OrderDTO;
import com.sq.sell.entity.OrderDetail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

public class OrderServiceImplTest extends SellApplicationTests {

    @Autowired
    private OrderServiceImpl orderService;
    @Test
    public void create() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("164103465734242707");
        orderDetail.setProductQuantity(2);
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName("test brother");
        orderDTO.setOrderDetailList(Arrays.asList(orderDetail));
        orderDTO.setBuyerAddress("bupt");
        orderDTO.setBuyerPhone("123445");
        orderDTO.setCreateTime(new Date());
        orderDTO.setUpdateTime(new Date());
        orderDTO.setBuyerOpenid("lagaga");

        orderService.create(orderDTO);
    }
}