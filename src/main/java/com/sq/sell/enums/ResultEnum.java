package com.sq.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PARAM_ERROR(00,"参数错误"),
    PRODUCT_NOT_EXIT(0,"商品不存在"),
    PRODUCT_STOCK_ERROR(1,"商品库存不正确"),
    ORDER_NOT_EXIST(2,"订单不存在"),
    ORDER_DETAIL_NOT_EXIST(3,"订单详情不存在"),
    ORDER_STATUS_ERROR(4,"订单状态错误"),
    ORDER_UPDATE_FAIL(5,"订单更新失败"),
    ORDER_DETAIL_EMPTY(6,"订单详情不存在"),
    ORDER_PAY_STATUS_ERROR(7,"订单支付状态不正确"),
    CART_EMPTY(8,"购物车不能为空")
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
