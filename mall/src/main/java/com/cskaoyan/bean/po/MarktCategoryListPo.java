package com.cskaoyan.bean.po;

import com.cskaoyan.bean.MarketCategory;
import lombok.Data;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/16 21:06
 */
@Data
public class MarktCategoryListPo {
    Long total;
    Integer pages;
    Long limit;
    Integer page;
    List<MarketCategory> list;

    public MarktCategoryListPo() {
    }

    public MarktCategoryListPo(Long total, Integer pages, Long limit, Integer page, List<MarketCategory> list) {
        this.total = total;
        this.pages = pages;
        this.limit = limit;
        this.page = page;
        this.list = list;
    }
}
