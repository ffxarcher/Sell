package com.sq.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SecondKill {
    @Id
    private String  productId;

    private Integer stockNum;

    private Integer buyNum;
}
