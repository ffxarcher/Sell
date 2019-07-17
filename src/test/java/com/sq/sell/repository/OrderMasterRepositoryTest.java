package com.sq.sell.repository;

import com.sq.sell.SellApplicationTests;
import com.sq.sell.entity.OrderMaster;
import com.sq.sell.utils.KeyUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class OrderMasterRepositoryTest extends SellApplicationTests {
    @Autowired
    private  OrderMasterRepository orderMasterRepository;

    @Test
    @Ignore
    public void orderMasterRepositoryTest()
    {
        PageRequest pageRequest = new PageRequest(0,1);

        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid("1101110",pageRequest);

        System.out.println(orderMasters.get().map(OrderMaster::getBuyerName).collect(Collectors.toList()));
    }

    @Test
    public void orderMasterRepositorySaveTest()
    {
        OrderMaster orderMaster = new OrderMaster();

        orderMaster.setOrderId(KeyUtil.getUniqueKey());
        orderMaster.setUpdateTime(new Date());
        orderMaster.setCreateTime(new Date());
        orderMaster.setOrderAmount(new BigDecimal(15));
        orderMaster.setBuyerAddress("hahah");
        orderMaster.setBuyerName("huahua");
        orderMaster.setBuyerOpenid("lala");
        orderMaster.setBuyerPhone("18619213");

        orderMasterRepository.save(orderMaster);

    }


}