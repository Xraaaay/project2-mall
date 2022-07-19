package com.cskaoyan.controller.wx.cart;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.service.wx.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车
 * @since 2022/07/19 10:31
 * @author lyx
 */

@RestController
@RequestMapping("wx/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @RequestMapping("checked")
    public BaseRespVo chaecked() {


        return BaseRespVo.ok(null);
    }


}
