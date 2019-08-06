package com.sq.sell.service;

public interface SecKillService {

    String queryMap(String productId);

    String querySecKillProductInfo(String productId);

    void orderProductMockDiffuser(String productId);
}
