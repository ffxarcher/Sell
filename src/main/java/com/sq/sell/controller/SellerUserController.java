package com.sq.sell.controller;

import com.sq.sell.config.ProjectConfig;
import com.sq.sell.constant.CookieConstant;
import com.sq.sell.constant.RedisConstant;
import com.sq.sell.entity.SellerInfo;
import com.sq.sell.enums.ResultEnum;
import com.sq.sell.service.SellerService;
import com.sq.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectConfig projectConfig;

    @Autowired
    private SellerService sellerService;

    /**
     * 使用nat的时候请注意cookie跨域的问题
     *
     * @param openid
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {
        //1、与数据库的数据进行匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);

        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url", "https://www.baidu.com");
            return new ModelAndView("common/error", map);
        }
        //2、匹配完成后写入将openid写入redis

        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.Expire;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid
                , expire, TimeUnit.SECONDS);

        //3、将token写入cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.Expire);

        return new ModelAndView("redirect:" + projectConfig.getSell() + "/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String,Object> map)
    {
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie != null)
        {
            redisTemplate.opsForValue().getOperations()
                    .delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }

        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMsg());
        map.put("url","/sell/seller/form");
        return new ModelAndView("common/success",map);
    }
}
