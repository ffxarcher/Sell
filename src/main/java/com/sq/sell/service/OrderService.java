package com.sq.sell.service;

import com.sq.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    /**创建订单*/
    OrderDTO create(OrderDTO orderDTO);
    /**查询单个订单*/
    OrderDTO findOne(String orderId);
    /**查询用户订单列表*/
    Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);
    /**取消订单*/
    OrderDTO finish(OrderDTO orderDTO);
    /**完成订单*/
    OrderDTO cancel(OrderDTO orderDTO);
    /**支付订单*/
    OrderDTO paid(OrderDTO orderDTO);
    /**查询所有 订单列表*/
    Page<OrderDTO> findList(Pageable pageable);
}
