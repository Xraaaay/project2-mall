package com.cskaoyan.controller.admin.mallmanagement;


import com.cskaoyan.anno.OperationLog;
import com.cskaoyan.bean.admin.comment.bo.CommentBo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderDetailPo;
import com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderListPo;
import com.cskaoyan.bean.admin.mallmanagement.bo.MarketOrderListBo;
import com.cskaoyan.bean.admin.mallmanagement.bo.MarketOrderShipBo;
import com.cskaoyan.service.admin.mallmanagement.OrderService;
import com.cskaoyan.util.Constant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
     * @return com.cskaoyan.bean.common.BaseRespVo<com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderListPo>
     * @author changyong
     * @since 2022/07/17 18:17
     */
    @RequiresPermissions("admin:order:list")
    @RequestMapping("list")
    public BaseRespVo<MarketOrderListPo> list(MarketOrderListBo marketOrderListBo) {
        MarketOrderListPo list = orderService.list(marketOrderListBo);
        return BaseRespVo.ok(list);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return com.cskaoyan.bean.common.BaseRespVo<com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderDetailPo>
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
    @RequiresPermissions("admin:order:ship")
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
    @RequiresPermissions("admin:order:refund")
    @RequestMapping("refund")
    public BaseRespVo refund(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        Integer refundMoney = (Integer) map.get("refundMoney");
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
    @RequiresPermissions("admin:order:delete")
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        orderService.delete(orderId);
        return BaseRespVo.ok(null);
    }

    /**
     * 返回快递列表
     *
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/19 11:11
     */
    @RequestMapping("channel")
    public BaseRespVo channel() {
        return BaseRespVo.ok(Constant.channelList);
    }

    /**
     * @description 商家回复
     * @author pqk
     * @date 2022/07/19 23:35
     */
    @RequiresPermissions("admin:order:reply")
    @RequestMapping("reply")
    public BaseRespVo reply(@RequestBody CommentBo commentBo){
        orderService.reply(commentBo);
        return BaseRespVo.ok(null);
    }
}
