package com.cskaoyan.bean.wx.cart;


import com.cskaoyan.bean.common.MarketCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 
 * @since 2022/07/19 11:29
 * @author lyx
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxCartVO {


    private CartTotalEntity cartTotal;
    private List<MarketCart> cartList;


}
