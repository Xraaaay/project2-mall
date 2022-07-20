package com.cskaoyan.bean.wx.order;

import com.cskaoyan.bean.common.MarketOrderGoods;
import lombok.Data;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/19 19:46
 */
@Data
public class WxOrderDetailPo {
    //TODO 参考前台上每个订单detail都没有expressInfo，不知道是什么
    String expressInfo;

    List<MarketOrderGoods> orderGoods;

    OrderOfDetail orderInfo;

    public WxOrderDetailPo() {
    }

    public WxOrderDetailPo(String expressInfo, List<MarketOrderGoods> orderGoods, OrderOfDetail orderOfDetail) {
        this.expressInfo = expressInfo;
        this.orderGoods = orderGoods;
        this.orderInfo = orderOfDetail;
    }
}
