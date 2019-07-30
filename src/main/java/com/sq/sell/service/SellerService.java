package com.sq.sell.service;

import com.sq.sell.entity.SellerInfo;

public interface SellerService {
    SellerInfo findSellerInfoByOpenid(String openid);

    SellerInfo findBySellerNameAndPassword(String sellerName,String password);
}
