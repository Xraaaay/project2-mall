package com.cskaoyan.bean.po;

import com.cskaoyan.bean.MarketRegion;
import lombok.Data;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/16 15:00
 */
@Data
public class MarketRegionPo {
    Long total;
    Integer pages;
    Long limit;
    Integer page;
    List<MarketRegion> list;

    public MarketRegionPo() {
    }

    public MarketRegionPo(Long total, Integer pages, Long limit, Integer page, List<MarketRegion> list) {
        this.total = total;
        this.pages = pages;
        this.limit = limit;
        this.page = page;
        this.list = list;
    }
}
