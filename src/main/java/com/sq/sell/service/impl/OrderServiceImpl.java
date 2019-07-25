package com.sq.sell.service.impl;

import com.sq.sell.converter.OrderMaster2OrderDTOConverter;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sq.sell.dto.OrderDTO;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    /**
     * 创建一个新的订单
     * @param orderDTO
     * @return
     */
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

    /**
     * 根据订单号返回订单+订单详情的列表
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {

        Optional orderMasterOptional =  orderMasterRepository.findById(orderId);

        if(!orderMasterOptional.isPresent())
        {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        OrderMaster orderMaster = (OrderMaster)orderMasterOptional.get();

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());

        if(CollectionUtils.isEmpty(orderDetailList))
        {
            throw  new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 根据用户的id和设定的页数查询订单列表
     * @param buyerOpenId
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {

        Page<OrderMaster> orderMasterPage  = orderMasterRepository.findByBuyerOpenid(buyerOpenId,pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
        {
            log.error("【完成订单】订单状态不正确，orderId={},  orderStatus={}"
                    ,orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null)
        {
            log.error("【完成订单】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        /**
         * 更新订单状态需要操作OrderMaster对象，OrderDTO仅作为数据传输对象使用
         */
        OrderMaster orderMaster = new OrderMaster();

        //查询订单状态(只有新订单才可以被取消，完成的不可以取消)
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
        {
            log.error("【取消订单】订单状态不正确，orderId={},  orderStatus={}"
                    ,orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult =  orderMasterRepository.save(orderMaster);
        if(updateResult == null)
        {
            log.error("【完成订单】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返还库存

        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList()))
        {
            log.error("【取消订单】订单无商品详情，orderDto={} ",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        List<CarDTO> carDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                    new CarDTO(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList());

        productService.increaseStock(carDTOList);
        //如果已支付，需要退款

        if(orderDTO.getPayStatus().equals(PayStatusEnum.FINISH.getCode()))
        {
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
        {
            log.error("【订单支付完成】订单状态不正确，orderId={},  orderStatus={}"
                    ,orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode()))
        {

            log.error("【订单支付完成】订单状态不正确，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.FINISH.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null)
        {
            log.error("【完成订单】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter
                .converter(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }
}
