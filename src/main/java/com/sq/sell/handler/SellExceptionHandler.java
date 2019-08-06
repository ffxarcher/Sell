package com.sq.sell.handler;

import com.sq.sell.VO.ResultVo;
import com.sq.sell.config.ProjectConfig;
import com.sq.sell.exception.SellException;
import com.sq.sell.exception.SellerAuthorizeException;
import com.sq.sell.utils.ResultVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理中心，捕获异常并返回处理结果
 */
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

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVo handlerSellException(SellException e)
    {
        return  ResultVoUtil.error(e.getCode(),e.getMessage());
    }
}
