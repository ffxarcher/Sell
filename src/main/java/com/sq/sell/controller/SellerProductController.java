package com.sq.sell.controller;

import com.sq.sell.entity.ProductCategory;
import com.sq.sell.entity.ProductInfo;
import com.sq.sell.exception.SellException;
import com.sq.sell.form.ProductForm;
import com.sq.sell.service.CategoryService;
import com.sq.sell.service.ProductService;
import com.sq.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 完成卖家商品的部分开发上下架、修改商品，新增商品
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId")String productId,
                               Map<String, Object> map)
    {
        try{
            productService.onSale(productId);
        }catch (SellException e)
        {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId")String productId,
                               Map<String, Object> map)
    {
        try{
            productService.offSale(productId);
        }catch (SellException e)
        {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false)String productId,
                             Map<String,Object> map)
    {
        if(!StringUtils.isEmpty(productId))
        {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("/product/index",map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult
            ,Map<String,Object> map)
    {
        if(bindingResult.hasErrors())
        {
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            if(!StringUtils.isEmpty(productForm.getProductId()))
            {
                productInfo = productService.findOne(productForm.getProductId());
                productInfo.setUpdateTime(new Date());
            }else{
                productForm.setProductId(KeyUtil.getUniqueKey());
                productInfo.setCreateTime(new Date());
                productInfo.setUpdateTime(new Date());
            }
            BeanUtils.copyProperties(productForm, productInfo);
            productService.save(productInfo);
        }catch (SellException e)
        {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

}
