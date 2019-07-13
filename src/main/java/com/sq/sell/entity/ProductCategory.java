package com.sq.sell.entity;


import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 无参的构建函数是必须的，否则只会引用设定的构造函数
 */
@Data
@Entity
public class ProductCategory {
    /**
     * 设置自增字段选择模式否则会报错
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer categoryId;
    //类目名字
    private String categoryName;
    //类目编号
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName,Integer categoryType)
    {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }


}
