package com.cskaoyan.service.admin.mallmanagement;

import com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderDetailPo;
import com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderListPo;
import com.cskaoyan.bean.admin.mallmanagement.bo.MarketOrderListBo;

/**
 * @author changyong
 * @since 2022/07/16 22:50
 */

public interface OrderService {

    /**
     * 返回订单列表并根据条件查询
     *
     * @param marketOrderListBo
     * @return com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderListPo
     * @author changyong
     * @since 2022/07/17 18:17
     */
    MarketOrderListPo list(MarketOrderListBo marketOrderListBo);

    /**
     * 根据id查询
     *
     * @param id
     * @return com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderDetailPo
     * @author changyong
     * @since 2022/07/17 18:16
     */
    MarketOrderDetailPo detail(Integer id);

    /**
     * 订单发货
     *
     * @param orderId
     * @param shipChannel
     * @param shipSn
     * @return void
     * @author changyong
     * @since 2022/07/17 18:14
     */
    void ship(Integer orderId, String shipChannel, String shipSn);

    /**
     * 处理用户申请退款订单
     *
     * @param id
     * @param refundMoney
     * @return void
     * @author changyong
     * @since 2022/07/17 18:13
     */
    void refund(Integer id, Double refundMoney);

    /**
     * 删除订单
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/17 20:58
     */
    void delete(Integer id);
}
