package com.sq.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class ProductInfo {

    @Id
    private String productId;
    @NotNull
    private String productName;
    /**
     * 价格
     */
    private BigDecimal productPrice;
    /**
     * 商品库存
     */
    private Integer productStock;
    /**
     * 商品描述
     */
    private String productDescription;
    /**
     * 小图
     */
    private String productIcon;
    /**
     * 0正常1下架
     */
    private Integer productStatus;

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;


}
