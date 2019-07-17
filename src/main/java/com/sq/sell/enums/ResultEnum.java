package com.sq.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIT(0,"商品不存在"),
    PRODUCT_STOCK_ERROR(1,"商品库存不正确"),
    ORDER_NOT_EXIST(2,"订单不存在"),
    ORDER_DETAIL_NOT_EXIST(3,"订单详情不存在")
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
