package com.cskaoyan.bean.wx.order;

import lombok.Data;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/19 14:39
 */
@Data
public class WxOrderListPo {
    Long total;
    Integer pages;
    Long limit;
    Integer page;
    List<OrderOfList> list;

    public WxOrderListPo() {
    }

    public WxOrderListPo(Long total, Integer pages, Long limit, Integer page, List<OrderOfList> list) {
        this.total = total;
        this.pages = pages;
        this.limit = limit;
        this.page = page;
        this.list = list;
    }
}
