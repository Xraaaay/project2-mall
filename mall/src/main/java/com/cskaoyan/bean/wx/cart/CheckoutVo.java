package com.cskaoyan.bean.wx.cart;

import com.cskaoyan.bean.common.MarketAddress;
import com.cskaoyan.bean.common.MarketCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Xrw
 * @date 2022/7/20 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckoutVo {
    // 0
    private int grouponRulesId = 0;
    // 实际价格
    private BigDecimal actualPrice;
    // 订单总价格 = 实际价格？
    private BigDecimal orderTotalPrice;
    // 购物车表id
    private Integer cartId;
    // 这tm啥啊
    private Integer userCouponId;
    // 优惠券表id
    private Integer couponId;
    // 商品总价
    private BigDecimal goodsTotalPrice;
    // 收货地址表id
    private Integer addressId;
    // 0
    private int grouponPrice = 0;
    // 收货地址
    private MarketAddress checkedAddress;
    // 优惠金额
    private BigDecimal couponPrice;
    // 可用优惠券长度
    private Integer availableCouponLength;
    // 运费
    private BigDecimal freightPrice;
    // 已勾选的商品列表
    private List<MarketCart> checkedGoodsList;
}
