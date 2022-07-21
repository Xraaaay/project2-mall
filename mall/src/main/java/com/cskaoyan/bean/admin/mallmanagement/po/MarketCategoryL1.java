package com.cskaoyan.bean.admin.mallmanagement.po;

import lombok.Data;

/**
 * @author changyong
 * @since 2022/07/21 16:38
 */
@Data
public class MarketCategoryL1 {
    Integer value;
    String label;

    public MarketCategoryL1() {
    }

    public MarketCategoryL1(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
