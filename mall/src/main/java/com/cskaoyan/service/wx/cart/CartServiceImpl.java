package com.cskaoyan.service.wx.cart;

import com.cskaoyan.bean.wx.cart.WxCartVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyx
 * @since 2022/07/19 10:46
 */
@Service
public class CartServiceImpl implements CartService {

    @Override
    public WxCartVO checked(List<Integer> productIds, Integer isChecked) {
        return null;
    }
}
