package com.sq.sell.service;

import com.sq.sell.entity.ProductCategory;

import java.util.List;

public interface CategoryService {

    /**
     * 根据id查询商品的类别
     * @param categoryId
     * @return
     */
    ProductCategory findOne (Integer categoryId);

    List<ProductCategory> findAll();

    /**
     *
     * @param productCategoryList
     * @return
     */
    List<ProductCategory>  findByCategoryTypeIn(List<Integer> productCategoryList);

    ProductCategory save(ProductCategory productCategory);

}
