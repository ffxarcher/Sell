package com.sq.sell.controller;

import com.sq.sell.VO.ResultVo;
import com.sq.sell.converter.OrderForm2OrderDTOConverter;
import com.sq.sell.dto.OrderDTO;
import com.sq.sell.enums.ResultEnum;
import com.sq.sell.exception.SellException;
import com.sq.sell.form.OrderForm;
import com.sq.sell.service.BuyerService;
import com.sq.sell.service.OrderService;
import com.sq.sell.utils.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 尽量将逻辑放到service层操作
 * */
@Slf4j
@RestController
@RequestMapping("buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;
    //创建订单
    /**
     * @Valid注解为表单验证无法通过验证则返回错误信息
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            log.error("【创建订单】参数不正确, orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO  = OrderForm2OrderDTOConverter.converter(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList()))
        {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVoUtil.success(map);
    }

    //根据openId查询订单列表
    /**
     * 7.19
     * 时区的问题依旧没有解决
     *
     * 7.20解决时区问题
     * @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
     * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     * 在实体类前加入注解并且使用serverTimezone=CTT
     * @param openId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVo<OrderDTO> list(@RequestParam("openid")String openId,
                                   @RequestParam(value = "page",defaultValue = "0") Integer page,
                                   @RequestParam(value = "size",defaultValue = "10") Integer size)
    {
        if(StringUtils.isEmpty(openId))
        {
            log.error("【查询订单列表】openId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage =  orderService.findList(openId,pageRequest);
        if(CollectionUtils.isEmpty(orderDTOPage.getContent()))
        {
            log.error("【查询订单列表】所查询的openId不存在");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return ResultVoUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVo<OrderDTO> detail(@RequestParam("openid")String openId,
                                     @RequestParam("orderId")String orderId)
    {
        if(StringUtils.isEmpty(openId))
        {
            log.error("【查询订单详情】openId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        if(StringUtils.isEmpty(orderId))
        {
            log.error("【查询订单详情】orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        // 不安全的做法 已经改进
        OrderDTO orderDTO = buyerService.findOneOrder(orderId,openId);
        return ResultVoUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam("openid")String openId,
                           @RequestParam("orderId")String orderId)
    {
        if(StringUtils.isEmpty(openId))
        {
            log.error("【取消订单】openId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if(StringUtils.isEmpty(orderId))
        {
            log.error("【取消订单】orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrder(orderId,openId);
        return ResultVoUtil.success();
    }
}
