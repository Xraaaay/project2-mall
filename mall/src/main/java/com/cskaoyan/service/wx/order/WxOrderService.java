package com.cskaoyan.service.wx.order;

import com.cskaoyan.bean.common.MarketOrderGoods;
import com.cskaoyan.bean.wx.order.WxOrderCommentBo;
import com.cskaoyan.bean.wx.order.WxOrderDetailPo;
import com.cskaoyan.bean.wx.order.WxOrderListPo;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/19 10:44
 */

public interface WxOrderService {
    WxOrderListPo list(Integer showType,Integer page,Integer limit);

    WxOrderDetailPo detail(Integer id);

    void refund(Integer id);

    void comment(WxOrderCommentBo wxOrderCommentBo);

    List<MarketOrderGoods> goods(Integer orderId, Integer goodsId);

    void delete(Integer id);

    void confirm(Integer id);
}
