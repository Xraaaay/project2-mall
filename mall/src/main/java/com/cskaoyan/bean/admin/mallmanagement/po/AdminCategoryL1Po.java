package com.cskaoyan.bean.admin.mallmanagement.po;

import lombok.Data;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/21 16:36
 */
@Data
public class AdminCategoryL1Po {
    Long total;
    Integer pages;
    Long limit;
    Integer page;
    List<MarketCategoryL1> list;

    public AdminCategoryL1Po() {
    }

    public AdminCategoryL1Po(Long total, Integer pages, Long limit, Integer page, List<MarketCategoryL1> list) {
        this.total = total;
        this.pages = pages;
        this.limit = limit;
        this.page = page;
        this.list = list;
    }
}
