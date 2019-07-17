package com.sq.sell.exception;

import com.sq.sell.enums.ResultEnum;

public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum)
    {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }


}
