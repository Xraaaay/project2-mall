package com.cskaoyan.bean.wx.order;

import lombok.Data;

/**
 * @author changyong
 * @since 2022/07/20 11:24
 */
@Data
public class WxOrderSubmitBo {
    Integer addressId;
    Integer cartId;
    Integer couponId;
    Integer grouponLinkId;
    Integer grouponRulesId;
    String message;
    Integer userCouponId;
}
