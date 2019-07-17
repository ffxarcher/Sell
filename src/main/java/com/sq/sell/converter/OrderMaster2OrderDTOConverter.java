package com.sq.sell.converter;

import com.sq.sell.dto.OrderDTO;
import com.sq.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {
    public static OrderDTO converter (OrderMaster orderMaster)
    {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> converter(List<OrderMaster> orderMasterList)
    {
        return orderMasterList.stream().map(
                e -> converter(e)
        ).collect(Collectors.toList());
    }
}
