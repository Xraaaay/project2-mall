package com.cskaoyan.service.wx.order;

import com.cskaoyan.bean.common.MarketOrderGoods;
import com.cskaoyan.bean.wx.order.*;


/**
 * @author changyong
 * @since 2022/07/19 10:44
 */

public interface WxOrderService {
    /**
     * 返回订单列表
     *
     * @param showType
     * @param page
     * @param limit
     * @return com.cskaoyan.bean.wx.order.WxOrderListPo
     * @author changyong
     * @since 2022/07/20 11:36
     */
    WxOrderListPo list(Integer showType, Integer page, Integer limit);

    /**
     * 由orderId返回订单详情
     *
     * @param id
     * @return com.cskaoyan.bean.wx.order.WxOrderDetailPo
     * @author changyong
     * @since 2022/07/20 11:37
     */
    WxOrderDetailPo detail(Integer id);

    /**
     * 用户申请退款
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/20 11:38
     */
    void refund(Integer id);

    /**
     * 商品评论
     *
     * @param wxOrderCommentBo
     * @return void
     * @author changyong
     * @since 2022/07/20 11:39
     */
    void comment(WxOrderCommentBo wxOrderCommentBo);

    /**
     * 评价前商品数据回显
     *
     * @param orderId
     * @param goodsId
     * @return com.cskaoyan.bean.common.MarketOrderGoods
     * @author changyong
     * @since 2022/07/20 11:40
     */
    MarketOrderGoods goods(Integer orderId, Integer goodsId);

    /**
     * 逻辑删除订单
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/20 11:41
     */
    void delete(Integer id);

    /**
     * 用户确认收货
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/20 11:42
     */
    void confirm(Integer id);

    /**
     * 未付款订单取消订单
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/20 11:43
     */
    void cancel(Integer id);

    /**
     * 提交订单
     *
     * @param wxOrderSubmitBo
     * @return com.cskaoyan.bean.wx.order.WxOrderSubmitPo
     * @author changyong
     * @since 2022/07/20 11:44
     */
    WxOrderSubmitPo submit(WxOrderSubmitBo wxOrderSubmitBo);

    /**
     * 付款
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/21 17:33
     */
    void prepay(Integer id);
}
