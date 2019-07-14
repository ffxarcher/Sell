package com.sq.sell.controller;

import com.sq.sell.VO.ProductInfoVo;
import com.sq.sell.VO.ProductVo;
import com.sq.sell.VO.ResultVo;
import com.sq.sell.entity.ProductCategory;
import com.sq.sell.entity.ProductInfo;
import com.sq.sell.service.CategoryService;
import com.sq.sell.service.ProductService;
import com.sq.sell.utils.ResultVoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVo list() {
        /**
         *  1、查询所有在架商品
         */
        List<ProductInfo> productInfoList = productService.findUpAll();
        /**
         * 2、查询商品包含的类目构成商品类目的list
         */
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        /**
         * 3、数据的拼装
         */

        List<ProductVo> productVoList = new ArrayList<>();

        for (ProductCategory productCategory : productCategoryList) {
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                ProductInfoVo productInfoVo = new ProductInfoVo();
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }

            productVo.setProductInfoVoList(productInfoVoList);
            productVoList.add(productVo);
        }


        return ResultVoUtil.success(productVoList);
    }

}
