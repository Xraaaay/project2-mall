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
    MarketAftersale marketAftersale;
    MarketOrder marketOrder;
    List<MarketOrderGoods> marketOrderGoodsList;

    public WxAftersaleDetailPo(MarketAftersale marketAftersale, MarketOrder marketOrder, List<MarketOrderGoods> marketOrderGoodsList) {
        this.marketAftersale = marketAftersale;
        this.marketOrder = marketOrder;
        this.marketOrderGoodsList = marketOrderGoodsList;
    }
}
