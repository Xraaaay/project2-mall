package com.cskaoyan.controller.mallManagementController;


import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.po.MarketOrderListPo;
import com.cskaoyan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changyong
 * @since 2022/07/15 22:23
 */
@RestController
@RequestMapping("admin/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("list")
    public BaseRespVo<MarketOrderListPo> list(Integer page, Integer limit, String sort, String order, String start, String end){
        MarketOrderListPo list = orderService.list(page, limit, sort, order, start, end);
        return BaseRespVo.ok(list);
    }

}
