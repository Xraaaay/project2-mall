package com.cskaoyan.controller.wx.aftersale;


import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.wx.aftersale.WxAftersaleDetailPo;
import com.cskaoyan.bean.wx.aftersale.WxAftersaleSubmitBo;
import com.cskaoyan.service.wx.aftersale.AftersaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 申请售后
 *
 * @author changyong
 * @since 2022/07/20 20:26
 */
@RestController
@RequestMapping("wx/aftersale")
public class AftersaleController {

    @Autowired
    AftersaleService afterSaleService;

    /**
     * 点击“申请售后”，将售后信息提交给后台
     *
     * @param wxAfterSaleSubmitBo
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 22:35
     */
    @RequestMapping("submit")
    public BaseRespVo submit(@RequestBody WxAftersaleSubmitBo wxAfterSaleSubmitBo) {
        afterSaleService.submit(wxAfterSaleSubmitBo);
        return BaseRespVo.ok(null);
    }

    /**
     * 提交申请售后信息后，再次点击“申请售后”，返回售后信息详情
     *
     * @param orderId
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 22:38
     */
    @RequestMapping("detail")
    public BaseRespVo detail(Integer orderId) {
        WxAftersaleDetailPo wxAftersaleDetailPo = afterSaleService.detail(orderId);
        return BaseRespVo.ok(wxAftersaleDetailPo);
    }
}
