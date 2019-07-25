package com.sq.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用已废弃
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeXinController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code")String code)
    {
        log.info("进入验证方法。。。");
        log.info("code={}",code);
    }
}
