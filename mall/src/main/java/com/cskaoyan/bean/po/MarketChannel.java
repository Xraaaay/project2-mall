package com.cskaoyan.bean.po;

import lombok.Data;

/**
 * @author changyong
 * @since 2022/07/19 10:59
 */
@Data
public class MarketChannel {
    String code;
    String name;

    public MarketChannel() {
    }

    public MarketChannel(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
