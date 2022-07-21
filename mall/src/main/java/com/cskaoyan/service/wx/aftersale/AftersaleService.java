package com.cskaoyan.service.wx.aftersale;

import com.cskaoyan.bean.wx.aftersale.WxAftersaleDetailPo;
import com.cskaoyan.bean.wx.aftersale.WxAftersaleSubmitBo;

/**
 * @author changyong
 * @since 2022/07/20 20:27
 */

public interface AftersaleService {
    /**
     * 点击“申请售后”，将售后信息提交给后台
     *
     * @param wxAftersaleSubmitBo
     * @return void
     * @author changyong
     * @since 2022/07/20 22:36
     */
    void submit(WxAftersaleSubmitBo wxAftersaleSubmitBo);

    /**
     * 提交申请售后信息后，再次点击“申请售后”，返回售后信息详情
     *
     * @param orderId
     * @return com.cskaoyan.bean.wx.aftersale.WxAftersaleDetailPo
     * @author changyong
     * @since 2022/07/20 22:37
     */
    WxAftersaleDetailPo detail(Integer orderId);
}
