package com.cskaoyan.bean;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MarketTopic {
    private Integer id;

    private String title;

    private String subtitle;

    @DecimalMin(value = "0", message = "商品低价输入错误")
    private BigDecimal price;

    @Pattern(regexp = "^\\d{1,}+k$|((^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*)+k$)",
            message = "阅读量输入错误(exp:10.1k)")
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