package com.sq.sell.service;

import com.sq.sell.dto.OrderDTO;

public interface BuyerService {
    /**
     * 查询对应openId的订单
     * @param orderId
     * @param openId
     * @return
     */
     OrderDTO findOneOrder(String orderId,String openId);

     OrderDTO cancelOrder(String orderId,String openId);
}
