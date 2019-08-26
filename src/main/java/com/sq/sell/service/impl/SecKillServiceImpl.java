package com.sq.sell.service.impl;

import com.sq.sell.entity.SecondKill;
import com.sq.sell.exception.SellException;
import com.sq.sell.repository.SecondKillRepository;
import com.sq.sell.service.RedisLock;
import com.sq.sell.service.SecKillService;
import com.sq.sell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
@Transactional
public class SecKillServiceImpl implements SecKillService {

    private static final int TIMEOUT = 10 * 1000;
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private SecondKillRepository secondKillRepository;

    static Map<String,Integer> products;
    static Map<String,Integer> stocks;
    static Map<String,String> orders;

    static String productId = "123456";

    static {
        products = new HashMap<>();
        stocks = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456",10000);
        stocks.put("123456",10000);

    }


    @Override
    public String queryMap(String productId) {

        SecondKill secondKill = secondKillRepository.findByProductId(productId);

        return "限量份："+"10000"+"还剩:"+
                secondKill.getStockNum()+"  已成功下单人数:"+
                secondKill.getBuyNum();
    }

    @Override
    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public   void orderProductMockDiffuser(String productId) {

        long time = System.currentTimeMillis() + TIMEOUT;

        if(!redisLock.lock(productId,String.valueOf(time)))
        {
            throw new SellException(110,"请稍等");
        }

        SecondKill secondKill = secondKillRepository.findByProductId(productId);

        int stockNum = secondKill.getStockNum();
        int buyNum = secondKill.getBuyNum();

        if(stockNum==0)
        {
            throw new SellException(100,"活动结束");

        }else{
            stockNum--;
            buyNum++;
            SecondKill mSecondKill = new SecondKill();
            mSecondKill.setProductId(productId);
            mSecondKill.setStockNum(stockNum);
            mSecondKill.setBuyNum(buyNum);
            secondKillRepository.save(mSecondKill);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

       redisLock.unlock(productId,String.valueOf(time));
    }
}
