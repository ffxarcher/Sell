package com.sq.sell.repository.mapper;


import com.sq.sell.entity.ProductCategory;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 完成了mybatis的基础测试
 */
@Repository
public interface ProductCategoryMapper {

    ProductCategory findById(Integer id);

    @Select("select * from product_category where category_name = #{name}")
    ProductCategory findByName(String name);
}
