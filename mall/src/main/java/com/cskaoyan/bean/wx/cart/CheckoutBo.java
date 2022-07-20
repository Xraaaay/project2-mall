package com.cskaoyan.bean.wx.cart;

import lombok.Data;

/**
 * @author Xrw
 * @date 2022/7/20 16:48
 */
@Data
public class CheckoutBo {
    Integer cartId;
    Integer addressId;
    Integer couponId;
    Integer userCouponId;
    Integer grouponRulesId;
}
