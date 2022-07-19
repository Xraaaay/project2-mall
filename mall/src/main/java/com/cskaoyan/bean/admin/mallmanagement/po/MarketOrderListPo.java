package com.cskaoyan.bean.admin.mallmanagement.po;

import com.cskaoyan.bean.common.MarketOrder;
import lombok.Data;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/16 22:53
 */
@Data
public class MarketOrderListPo {
    Long total;
    Integer pages;
    Long limit;
    Integer page;
    List<MarketOrder> list;

    public MarketOrderListPo() {
    }

    public MarketOrderListPo(Long total, Integer pages, Long limit, Integer page, List<MarketOrder> list) {
        this.total = total;
        this.pages = pages;
        this.limit = limit;
        this.page = page;
        this.list = list;
    }
}
