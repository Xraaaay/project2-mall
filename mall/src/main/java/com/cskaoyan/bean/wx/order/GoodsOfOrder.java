package com.cskaoyan.bean.wx.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author changyong
 * @since 2022/07/19 14:50
 */
@Data
public class GoodsOfOrder {
    Integer id;
    Short number;
    String goodsName;
    String picUrl;
    BigDecimal price;
    String[] specifications;

    public GoodsOfOrder() {
    }

    public GoodsOfOrder(Integer id, Short number, String goodsName, String picUrl, BigDecimal price, String[] specifications) {
        this.id = id;
        this.number = number;
        this.goodsName = goodsName;
        this.picUrl = picUrl;
        this.price = price;
        this.specifications = specifications;
    }
}
