package com.sq.sell.controller;

import com.sq.sell.config.ProjectConfig;
import com.sq.sell.constant.CookieConstant;
import com.sq.sell.constant.RedisConstant;
import com.sq.sell.entity.SellerInfo;
import com.sq.sell.form.SellerForm;
import com.sq.sell.service.SellerService;
import com.sq.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 表单登录方法完成
 */
@Controller
@RequestMapping("/seller")
public class SellerFormLogin {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectConfig projectConfig;

    @GetMapping("/form")
    public ModelAndView form(Map<String,Object> map)
    {
        return new ModelAndView("seller/form",map);
    }

    @PostMapping("/form_login")
    public ModelAndView myLogin(@Valid SellerForm sellerForm,
                              BindingResult bindingResult,
                              HttpServletResponse response,
                              Map<String,Object> map)
    {
        if(bindingResult.hasErrors())
        {
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/form_login");
            return new ModelAndView("common/error",map);
        }

        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo = sellerService.findBySellerNameAndPassword
                    (sellerForm.getSellerName(),sellerForm.getPassword());

        if(sellerInfo == null)
        {
            map.put("msg","用户不存在");
            map.put("url","/sell/seller/form");
            return new ModelAndView("common/error",map);
        }


        String openid = sellerInfo.getOpenid();
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.Expire;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid
                , expire, TimeUnit.SECONDS);
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.Expire);
        return new ModelAndView("redirect:" + projectConfig.getSell() + "/sell/seller/order/list");

    }

}
