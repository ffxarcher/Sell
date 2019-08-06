package com.sq.sell.service.impl;

import com.sq.sell.exception.SellException;
import com.sq.sell.service.RedisLock;
import com.sq.sell.service.SecKillService;
import com.sq.sell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class SecKillServiceImpl implements SecKillService {

    private static final int TIMEOUT = 10 * 1000;
    @Autowired
    private RedisLock redisLock;

    static Map<String,Integer> products;
    static Map<String,Integer> stocks;
    static Map<String,String> orders;

    static {
        products = new HashMap<>();
        stocks = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456",10000);
        stocks.put("123456",10000);

    }


    @Override
    public String queryMap(String productId) {
        return "限量份："+products.get(productId)+"还剩:"+
                stocks.get(productId)+"  已成功下单人数:"+
                orders.size();
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

        int stockNum = stocks.get(productId);
        if(stockNum==0)
        {
            throw new SellException(100,"活动结束");

        }else{
            orders.put(KeyUtil.getUniqueKey(),productId);

            stockNum--;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stocks.put(productId,stockNum);

        }

        redisLock.unlock(productId,String.valueOf(time));
    }
}
