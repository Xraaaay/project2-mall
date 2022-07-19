package com.cskaoyan.bean.wx.order;

import com.cskaoyan.bean.admin.mallmanagement.po.HandleOption;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author changyong
 * @since 2022/07/19 19:48
 */
@Data
public class OrderOfDetail {

    BigDecimal actualPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date addTime;

    String address;

    Short aftersaleStatus;

    String consignee;

    BigDecimal couponPrice;

    String expCode;

    String expName;

    String expNo;

    BigDecimal freightPrice;

    BigDecimal goodsPrice;

    HandleOption handleOption;

    Integer id;

    String message;

    String mobile;

    String orderSn;

    String orderStatusText;

    public OrderOfDetail() {
    }

    public OrderOfDetail(BigDecimal actualPrice, Date addTime, String address, Short aftersaleStatus, String consignee, BigDecimal couponPrice, String expCode, String expName, String expNo, BigDecimal freightPrice, BigDecimal goodsPrice, HandleOption handleOption, Integer id, String message, String mobile, String orderSn, String orderStatusText) {
        this.actualPrice = actualPrice;
        this.addTime = addTime;
        this.address = address;
        this.aftersaleStatus = aftersaleStatus;
        this.consignee = consignee;
        this.couponPrice = couponPrice;
        this.expCode = expCode;
        this.expName = expName;
        this.expNo = expNo;
        this.freightPrice = freightPrice;
        this.goodsPrice = goodsPrice;
        this.handleOption = handleOption;
        this.id = id;
        this.message = message;
        this.mobile = mobile;
        this.orderSn = orderSn;
        this.orderStatusText = orderStatusText;
    }
}
