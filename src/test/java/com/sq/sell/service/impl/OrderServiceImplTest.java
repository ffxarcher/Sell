package com.sq.sell.service.impl;

import com.sq.sell.SellApplicationTests;
import com.sq.sell.dto.OrderDTO;
import com.sq.sell.entity.OrderDetail;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * orderService findOne 测试完成
 * cancel功能测试完成，还有返回支付金额待Todo
 * finish功能测试完成
 * 查找所有订单完成测试
 */

public class OrderServiceImplTest extends SellApplicationTests {

    @Autowired
    private OrderServiceImpl orderService;
    @Test
    @Ignore
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

    @Test
    public void findOne()
    {
        System.out.println(orderService.findOne("123567").getOrderDetailList());
    }

    @Test
    @Ignore
    public void findList()
    {
        PageRequest pageRequest = new PageRequest(0,4);
        System.out.println(orderService.findList("1101110",pageRequest).getContent());
        System.out.println(orderService.findList("1101110",pageRequest).getTotalElements());
    }

    @Test
    @Ignore
    public void cancelTest()
    {
        OrderDTO orderDTO = orderService.findOne("1234567");
        orderDTO = orderService.cancel(orderDTO);
        System.out.println(orderDTO.getOrderStatus());
    }


    @Test
    public void finishTest()
    {
        OrderDTO orderDTO = orderService.findOne("1234567");
        orderDTO = orderService.finish(orderDTO);
        System.out.println(orderDTO.getOrderStatus());
    }

    @Test
    public void paidTest()
    {
        OrderDTO orderDTO = orderService.findOne("1234567");
        orderDTO = orderService.paid(orderDTO);
        System.out.println(orderDTO.getPayStatus());
    }

    @Test
    public void findAll()
    {
        PageRequest pageRequest = new PageRequest(0,4);
        System.out.println(orderService.findList(pageRequest).getContent());
    }

}