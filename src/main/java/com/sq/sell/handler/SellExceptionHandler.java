package com.sq.sell.handler;

import com.sq.sell.config.ProjectConfig;
import com.sq.sell.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private  ProjectConfig projectConfig;

    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException()
    {
        return new ModelAndView("redirect:"
                .concat(projectConfig.getSell())
                .concat("/sell/seller/form"));
    }
}
