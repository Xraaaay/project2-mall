package com.cskaoyan.controller.wx.order;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.service.wx.order.WxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        //wxOrderService.list();
        return null;
    }
}
