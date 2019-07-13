package com.sq.sell.repository;

import com.sq.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductInfo,String> {
    List<ProductInfo> findByProductStatus (Integer productStatus);
}
