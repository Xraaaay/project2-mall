package com.cskaoyan.controller.mallmanagement;


import com.cskaoyan.anno.OperationLog;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.po.MarketOrderDetailPo;
import com.cskaoyan.bean.po.MarketOrderListPo;
import com.cskaoyan.bean.bo.MarketOrderListBo;
import com.cskaoyan.bean.bo.MarketOrderShipBo;
import com.cskaoyan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author changyong
 * @since 2022/07/15 22:23
 */
@RestController
@RequestMapping("admin/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 返回订单列表并根据条件查询
     *
     * @param marketOrderListBo
     * @return com.cskaoyan.bean.common.BaseRespVo<com.cskaoyan.bean.po.MarketOrderListPo>
     * @author changyong
     * @since 2022/07/17 18:17
     */
    @RequestMapping("list")
    public BaseRespVo<MarketOrderListPo> list(MarketOrderListBo marketOrderListBo) {
        MarketOrderListPo list = orderService.list(marketOrderListBo);
        return BaseRespVo.ok(list);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return com.cskaoyan.bean.common.BaseRespVo<com.cskaoyan.bean.po.MarketOrderDetailPo>
     * @author changyong
     * @since 2022/07/17 18:16
     */
    @RequestMapping("detail")
    public BaseRespVo<MarketOrderDetailPo> detail(Integer id) {
        MarketOrderDetailPo marketOrderDetailPo = orderService.detail(id);
        return BaseRespVo.ok(marketOrderDetailPo);
    }

    /**
     * 订单发货
     *
     * @param marketOrderShipBo
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/17 18:15
     */
    // AOP 订单操作日志xrw
    @OperationLog(action = "订单发货", type = 2)
    @RequestMapping("ship")
    public BaseRespVo ship(@RequestBody MarketOrderShipBo marketOrderShipBo) {
        orderService.ship(marketOrderShipBo.getOrderId(), marketOrderShipBo.getShipChannel(), marketOrderShipBo.getShipSn());
        return BaseRespVo.ok(null);
    }

    /**
     * 处理用户申请退款订单
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/17 18:13
     */
    @RequestMapping("refund")
    public BaseRespVo refund(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        Double refundMoney = (Double) map.get("refundMoney");
        orderService.refund(orderId, refundMoney);
        return BaseRespVo.ok(null);
    }

    /**
     * 删除订单
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/17 20:58
     */
    // AOP 订单操作日志xrw
    @OperationLog(action = "删除订单", type = 2)
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        orderService.delete(orderId);
        return BaseRespVo.ok(null);
    }
}
