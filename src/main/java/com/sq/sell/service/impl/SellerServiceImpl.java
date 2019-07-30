package com.sq.sell.service.impl;

import com.sq.sell.entity.SellerInfo;
import com.sq.sell.repository.SellerInfoRepository;
import com.sq.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }

    @Override
    public SellerInfo findBySellerNameAndPassword(String sellerName, String password) {
        return sellerInfoRepository.findBySellerNameAndPassword(sellerName,password);
    }
}
