package com.cskaoyan.bean.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketCart {
    private Integer id;

    private Integer userId;

    private Integer goodsId;

    private String goodsSn;

    private String goodsName;

    private Integer productId;

    private BigDecimal price;

    private Short number;

    private String[] specifications;

    private Boolean checked;

    private String picUrl;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;


}