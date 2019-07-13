package com.sq.sell.VO;

import lombok.Data;

/**
 * 返回给前端的视图对象
 */
@Data
public class ResultVo<T> {
    //状态码
    private Integer code;
    //信息
    private String message;
    //具体内容
    private T data;
}
