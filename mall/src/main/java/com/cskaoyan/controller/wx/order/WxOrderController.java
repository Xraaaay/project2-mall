package com.cskaoyan.controller.wx.order;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.MarketOrderGoods;
import com.cskaoyan.bean.wx.order.WxOrderCommentBo;
import com.cskaoyan.bean.wx.order.WxOrderDetailPo;
import com.cskaoyan.bean.wx.order.WxOrderListPo;
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

    @RequestMapping("list")
    public BaseRespVo list(Integer showType,Integer page,Integer limit){
        WxOrderListPo wxOrderListPo = wxOrderService.list(showType, page, limit);
        return BaseRespVo.ok(wxOrderListPo);
    }

    @RequestMapping("detail")
    public BaseRespVo detail(Integer orderId){
        WxOrderDetailPo wxOrderDetailPo = wxOrderService.detail(orderId);
        return BaseRespVo.ok(wxOrderDetailPo);
    }

    @RequestMapping("refund")
    public BaseRespVo refund(@RequestBody Map map){
        Integer orderId = (Integer) map.get("orderId");
        wxOrderService.refund(orderId);
        return BaseRespVo.ok(null);
    }

    @RequestMapping("goods")
    public BaseRespVo goods(Integer orderId,Integer goodsId){
        List<MarketOrderGoods> goods = wxOrderService.goods(orderId, goodsId);
        return BaseRespVo.ok(goods);
    }

    @RequestMapping("comment")
    public BaseRespVo comment(@RequestBody WxOrderCommentBo wxOrderCommentBo){
        wxOrderService.comment(wxOrderCommentBo);
        return BaseRespVo.ok(null);
    }

    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Map map){
        Integer orderId = (Integer) map.get("orderId");
        wxOrderService.delete(orderId);
        return BaseRespVo.ok(null);
    }

    @RequestMapping("confirm")
    public BaseRespVo confirm(@RequestBody Map map){
        Integer orderId = (Integer) map.get("orderId");
        wxOrderService.confirm(orderId);
        return BaseRespVo.ok(null);
    }

    @RequestMapping("cannel")
    public BaseRespVo cannel(){
        return BaseRespVo.ok(null);
    }
}
