package com.cskaoyan.bean.wx.order;

import com.cskaoyan.bean.admin.mallmanagement.po.HandleOption;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author changyong
 * @since 2022/07/19 14:42
 */
@Data
public class OrderOfList {
    BigDecimal actualPrice;
    Short aftersaleStatus;
    List<GoodsOfOrder> goodsList;
    HandleOption handleOption;
    Integer id;
    boolean isGroupin;
    String orderSn;
    String orderStatusText;

    public OrderOfList() {
    }

    public OrderOfList(BigDecimal actualPrice, Short aftersaleStatus, List<GoodsOfOrder> goodsList,
                       HandleOption handleOption, Integer id, boolean isGroupin, String orderSn,
                       String orderStatusText) {
        this.actualPrice = actualPrice;
        this.aftersaleStatus = aftersaleStatus;
        this.goodsList = goodsList;
        this.handleOption = handleOption;
        this.id = id;
        this.isGroupin = isGroupin;
        this.orderSn = orderSn;
        this.orderStatusText = orderStatusText;
    }
}
