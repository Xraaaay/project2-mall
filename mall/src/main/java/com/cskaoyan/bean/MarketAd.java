package com.cskaoyan.bean;

import java.util.Date;

import lombok.Data;


@Data
public class MarketAd {
    private Integer id;

    private String name;

    private String link;

    private String url;

    private Byte position;

    private String content;

    private Date startTime;

    private Date endTime;

    private Boolean enabled;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;
}