package com.sq.sell.repository;

import com.sq.sell.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {
    SellerInfo findByOpenid (String openId);

    SellerInfo findBySellerNameAndPassword(String sellerName,String password);
}
