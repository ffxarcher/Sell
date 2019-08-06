package com.sq.sell.controller;

import com.sq.sell.service.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/skill")
public class Seckill {

    @Autowired
    private SecKillService secKillService;

    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId)
    {
        log.info("@skill : productId:"+productId);
        secKillService.orderProductMockDiffuser(productId);
        return secKillService.querySecKillProductInfo(productId);
    }

}
