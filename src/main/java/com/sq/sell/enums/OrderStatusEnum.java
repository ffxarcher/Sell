package com.sq.sell.enums;

import lombok.Getter;

/**
 * 枚举类的实例由编译器自动创建，在全局中只会存在一个
 * 可以避免定义的重名
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),
    FINISH(1,"完结"),
    CANCEL(2,"已取消");
    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
