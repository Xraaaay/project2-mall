package com.cskaoyan.bean;

import java.util.Date;

import lombok.Data;

import javax.validation.constraints.Pattern;


@Data
public class MarketAd {
    private Integer id;

    private String name;

    // URL或域名
    @Pattern(regexp = "[a-zA-z]+://[^\\s]*|[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?",
            message = "请输入正确的活动链接")
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