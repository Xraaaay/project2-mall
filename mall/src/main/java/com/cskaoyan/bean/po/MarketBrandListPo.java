package com.cskaoyan.bean.po;

import com.cskaoyan.bean.MarketBrand;
import lombok.Data;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/16 16:54
 */
@Data
public class MarketBrandListPo {
    Long total;
    Integer pages;
    Long limit;
    Integer page;
    List<MarketBrand> list;

    public MarketBrandListPo() {
    }

    public MarketBrandListPo(Long total, Integer pages, Long limit, Integer page, List<MarketBrand> list) {
        this.total = total;
        this.pages = pages;
        this.limit = limit;
        this.page = page;
        this.list = list;
    }
}
