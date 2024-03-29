package com.sq.sell.service;

import com.sq.sell.dto.CarDTO;
import com.sq.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    /**
     * 查询所有在价产品
     *
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询
     *
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CarDTO> carDTOList);


    //减库存
    void decrease(List<CarDTO> carDTOList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);


}
