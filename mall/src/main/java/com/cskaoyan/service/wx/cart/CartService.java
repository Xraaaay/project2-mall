package com.cskaoyan.service.wx.cart;

import com.cskaoyan.bean.wx.cart.WxCartVO;

import java.util.List;

/**
 /**
 * 购物车接口
 * @since 2022/07/19 10:45
 * @author lyx
 */
public interface CartService {

    WxCartVO checked(List<Integer> productIds, Integer isChecked);
}
