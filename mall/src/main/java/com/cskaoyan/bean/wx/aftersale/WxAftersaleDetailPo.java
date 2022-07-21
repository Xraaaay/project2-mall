package com.cskaoyan.bean.wx.aftersale;

import com.cskaoyan.bean.common.MarketAftersale;
import com.cskaoyan.bean.common.MarketOrder;
import com.cskaoyan.bean.common.MarketOrderGoods;
import lombok.Data;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/20 21:27
 */
@Data
public class WxAftersaleDetailPo {
    MarketAftersale aftersale;
    MarketOrder order;
    List<MarketOrderGoods> orderGoods;

    public WxAftersaleDetailPo() {
    }

    public WxAftersaleDetailPo(MarketAftersale aftersale, MarketOrder order, List<MarketOrderGoods> orderGoods) {
        this.aftersale = aftersale;
        this.order = order;
        this.orderGoods = orderGoods;
    }
}
