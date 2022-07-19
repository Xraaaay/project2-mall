package com.cskaoyan.bean.admin.mallmanagement.po;

import com.cskaoyan.bean.common.MarketOrder;
import com.cskaoyan.bean.common.MarketOrderGoods;
import com.cskaoyan.bean.common.MarketUser;
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
