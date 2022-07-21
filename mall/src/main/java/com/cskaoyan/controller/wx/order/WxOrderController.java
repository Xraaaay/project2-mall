package com.cskaoyan.controller.wx.order;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.MarketOrderGoods;
import com.cskaoyan.bean.wx.order.*;
import com.cskaoyan.service.wx.order.WxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author changyong
 * @since 2022/07/19 10:40
 */
@RestController
@RequestMapping("wx/order")
public class WxOrderController {

    @Autowired
    WxOrderService wxOrderService;

    /**
     * 返回订单列表
     *
     * @param showType
     * @param page
     * @param limit
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 11:35
     */
    @RequestMapping("list")
    public BaseRespVo list(Integer showType, Integer page, Integer limit) {
        WxOrderListPo wxOrderListPo = wxOrderService.list(showType, page, limit);
        return BaseRespVo.ok(wxOrderListPo);
    }

    /**
     * 由orderId返回订单详情
     *
     * @param orderId
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 11:37
     */
    @RequestMapping("detail")
    public BaseRespVo detail(Integer orderId) {
        WxOrderDetailPo wxOrderDetailPo = wxOrderService.detail(orderId);
        return BaseRespVo.ok(wxOrderDetailPo);
    }

    /**
     * 用户申请退款
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 11:37
     */
    @RequestMapping("refund")
    public BaseRespVo refund(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        wxOrderService.refund(orderId);
        return BaseRespVo.ok(null);
    }

    /**
     * 评价前商品数据回显
     *
     * @param orderId
     * @param goodsId
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 11:39
     */
    @RequestMapping("goods")
    public BaseRespVo goods(Integer orderId, Integer goodsId) {
        MarketOrderGoods goods = wxOrderService.goods(orderId, goodsId);
        return BaseRespVo.ok(goods);
    }

    /**
     * 商品评论
     *
     * @param wxOrderCommentBo
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 11:39
     */
    @RequestMapping("comment")
    public BaseRespVo comment(@RequestBody WxOrderCommentBo wxOrderCommentBo) {
        wxOrderService.comment(wxOrderCommentBo);
        return BaseRespVo.ok(null);
    }

    /**
     * 逻辑删除订单
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 11:41
     */
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        wxOrderService.delete(orderId);
        return BaseRespVo.ok(null);
    }

    /**
     * 用户确认收货
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 11:41
     */
    @RequestMapping("confirm")
    public BaseRespVo confirm(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        wxOrderService.confirm(orderId);
        return BaseRespVo.ok(null);
    }

    /**
     * 未付款订单取消订单
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 11:43
     */
    @RequestMapping("cancel")
    public BaseRespVo cancel(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        wxOrderService.cancel(orderId);
        return BaseRespVo.ok(null);
    }

    /**
     * 提交订单
     *
     * @param wxOrderSubmitBo
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 11:43
     */
    @RequestMapping("submit")
    public BaseRespVo submit(@RequestBody WxOrderSubmitBo wxOrderSubmitBo) {
        WxOrderSubmitPo wxOrderSubmitPo = wxOrderService.submit(wxOrderSubmitBo);
        return BaseRespVo.ok(wxOrderSubmitPo);
    }

    /**
     * 付款
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/21 17:33
     */
    @RequestMapping("prepay")
    public BaseRespVo prepay(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        wxOrderService.prepay(orderId);
        return BaseRespVo.ok(null);
    }
}
