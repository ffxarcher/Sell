package com.sq.sell.service;

import com.sq.sell.dto.OrderDTO;

public interface PushMessage {
    /**
     * 订单状态变更
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
