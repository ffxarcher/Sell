package com.sq.sell.repository;

import com.sq.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    /**
     * 按买家openid查询订单，并分页
     * @param openId
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid (String openId, Pageable pageable);
}
