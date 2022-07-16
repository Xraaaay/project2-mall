package com.cskaoyan.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MarketTopic {
    private Integer id;

    private String title;

    private String subtitle;

    private BigDecimal price;

    private String readCount;

    private String picUrl;

    private Integer sortOrder;

    // private String goods;
    private Integer[] goods;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private String content;
}