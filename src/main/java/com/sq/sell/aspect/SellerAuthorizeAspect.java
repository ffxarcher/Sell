package com.sq.sell.aspect;

import com.sq.sell.constant.CookieConstant;
import com.sq.sell.constant.RedisConstant;
import com.sq.sell.exception.SellerAuthorizeException;
import com.sq.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.sq.sell.controller.Seller*.*(..))" +
    "&& !execution(public * com.sq.sell.controller.SellerUserController.*(..))" +
    "&& !execution(public * com.sq.sell.controller.SellerFormLogin.*(..))"
    )
    public void verify(){}

    @Before("verify()")
    public void doVerify()
    {
        ServletRequestAttributes attributes
                    = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Cookie cookie;
        try{
             cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        }catch (Exception e)
        {
            log.warn("【登录校验】cookie中不存在token");
            throw new SellerAuthorizeException();
        }



        String tokenValue = redisTemplate.opsForValue()
                .get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

        if(StringUtils.isEmpty(tokenValue))
        {
            log.warn("【登录校验】redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}
