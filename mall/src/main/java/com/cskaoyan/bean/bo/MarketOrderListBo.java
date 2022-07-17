package com.cskaoyan.bean.bo;

import lombok.Data;

/**
 * @author changyong
 * @since 2022/07/17 09:59
 */
@Data
public class MarketOrderListBo {
    Integer page;
    Integer limit;
    // @JsonFormat(pattern = "yyyy-MM-dd")
    // Date[] timeArray;
    String[] timeArray=new String[2];
    String[] orderStatusArray=new String[10];
    String sort;
    String order;
    Integer orderId;
    String start;
    String end;
    // @JsonFormat(pattern = "yyyy-MM-dd")
    // Date start;
    // @JsonFormat(pattern = "yyyy-MM-dd")
    // Date end;
    Integer userId;
    String orderSn;
}
