package com.cskaoyan.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MarketCoupon {
    private Integer id;

    private String name;

    private String desc;

    private String tag;

    @Min(value = 0, message = "优惠券数量输入错误")
    private Integer total;

    @DecimalMin(value = "0", message = "满减金额输入错误")
    private BigDecimal discount;

    @DecimalMin(value = "0", message = "最低消费输入错误")
    private BigDecimal min;

    @Min(value = 0, message = "每人限领输入错误")
    private Short limit;

    private Short type;

    private Short status;

    private Short goodsType;

    // private String goodsValue;
    private String[] goodsValue;

    private String code;

    private Short timeType;

    @Min(value = 0, message = "有效期输入错误")
    private Short days;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}