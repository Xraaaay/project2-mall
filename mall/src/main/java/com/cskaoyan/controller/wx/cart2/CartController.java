package com.cskaoyan.controller.wx.cart2;

import com.cskaoyan.bean.MarketCart;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.wx.cart.WxCartVO;
import com.cskaoyan.service.wx.cart2.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public BaseRespVo checked(List<Integer> productIds, Integer isChecked) {
        WxCartVO wxCartVO = cartService.checked(productIds,isChecked);



        return BaseRespVo.ok(null);
    }


}
