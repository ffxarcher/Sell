package com.sq.sell.service.impl;

import com.sq.sell.dto.OrderDTO;
import com.sq.sell.enums.ResultEnum;
import com.sq.sell.exception.SellException;
import com.sq.sell.service.BuyerService;
import com.sq.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;

@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOneOrder(String orderId, String openId) {
        return checkOrderOwner(orderId,openId);
    }

    @Override
    public OrderDTO cancelOrder(String orderId, String openId) {
        OrderDTO orderDTO = checkOrderOwner(orderId,openId);
        if(orderDTO == null)
        {
            log.error("【查找订单】订单不存在，openId={}",openId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String orderId, String openId)
    {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null) return null;
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openId))
        {
            log.error("【查找订单】订单的openId不一致，openId={}, orderDTO={}",openId,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
