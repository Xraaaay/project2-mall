package com.cskaoyan.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketGoods {
    private Integer id;

    private Integer goodsSn;

    private String name;

    private Integer categoryId;

    private Integer brandId;

    private String gallery;

    private String keywords;

    private String brief;

    private Boolean isOnSale;

    private Short sortOrder;

    private String picUrl;

    private String shareUrl;

    private Boolean isNew;

    private Boolean isHot;

    private String unit;

    private BigDecimal counterPrice;

    private BigDecimal retailPrice;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private String detail;

}