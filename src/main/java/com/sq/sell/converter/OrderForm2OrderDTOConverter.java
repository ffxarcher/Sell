package com.sq.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sq.sell.dto.OrderDTO;
import com.sq.sell.entity.OrderDetail;
import com.sq.sell.enums.ResultEnum;
import com.sq.sell.exception.SellException;
import com.sq.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {

    /**
     * TypeToken返回泛型的正确类型参见
     * https://www.jianshu.com/p/c820e55d9f27
     * @param orderForm
     * @return
     */
    public static OrderDTO converter(OrderForm orderForm)
    {
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();

        try{
            orderDetailList =
                    gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
            orderDTO.setOrderDetailList(orderDetailList);
        }catch (Exception e)
        {
            log.error("【对象转换】错误，string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return orderDTO;
    }
}
