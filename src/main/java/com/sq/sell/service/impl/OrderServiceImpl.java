package com.sq.sell.service.impl;

import com.sq.sell.dto.CarDTO;
import com.sq.sell.entity.OrderDetail;
import com.sq.sell.entity.OrderMaster;
import com.sq.sell.entity.ProductInfo;
import com.sq.sell.enums.OrderStatusEnum;
import com.sq.sell.enums.PayStatusEnum;
import com.sq.sell.enums.ResultEnum;
import com.sq.sell.exception.SellException;
import com.sq.sell.repository.OrderDetailRepository;
import com.sq.sell.repository.OrderMasterRepository;
import com.sq.sell.service.OrderService;
import com.sq.sell.service.ProductService;
import com.sq.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sq.sell.dto.OrderDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.getUniqueKey();

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1、查询商品（数量、价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            //2、计算总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        //3、写入订单数据库
        OrderMaster orderMaster = new OrderMaster();

        /**
         *copyProperties会覆盖掉原来的数据，若orderDTO中的数据项为null则orderMaster中的数据相被置空
         * ！！！！！！一定要注意
         */

        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);


        //4、扣库存
        List<CarDTO> carDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CarDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decrease(carDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
