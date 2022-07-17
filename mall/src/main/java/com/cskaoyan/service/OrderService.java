package com.cskaoyan.service;

import com.cskaoyan.bean.po.MarketOrderDetailPo;
import com.cskaoyan.bean.po.MarketOrderListPo;
import com.cskaoyan.bean.bo.MarketOrderListBo;

/**
 * @author changyong
 * @since 2022/07/16 22:50
 */

public interface OrderService {

    MarketOrderListPo list(MarketOrderListBo marketOrderListBo);

    MarketOrderDetailPo detail(Integer id);

    void ship(Integer orderId,String shipChannel,String shipSn);
}
