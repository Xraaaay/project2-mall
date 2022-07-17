package com.cskaoyan.bean.po;

import com.cskaoyan.bean.MarketOrder;
import com.cskaoyan.bean.MarketOrderGoods;
import com.cskaoyan.bean.MarketUser;
import lombok.Data;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/17 08:45
 */
@Data
public class MarketOrderDetailPo {

    private List<MarketOrderGoods> orderGoods;
    private MarketUser user;
    private MarketOrder order;

    public MarketOrderDetailPo() {
    }

    public MarketOrderDetailPo(List<MarketOrderGoods> orderGoods, MarketUser user, MarketOrder order) {
        this.orderGoods = orderGoods;
        this.user = user;
        this.order = order;
    }
}
