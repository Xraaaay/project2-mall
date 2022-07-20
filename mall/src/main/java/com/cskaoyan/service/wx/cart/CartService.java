package com.cskaoyan.service.wx.cart;

import java.util.List;
import java.util.Map;

/**
 * /**
 * 购物车接口
 *
 * @author lyx
 * @since 2022/07/19 10:45
 */
public interface CartService {

    /**
     * 购物车页面：显示购物车商品
     *
     * @author Xrw
     * @date 2022/7/19 22:03
     */
    Map<String, Object> index();

    /**
     * 购物车页面：勾选或取消勾选商品
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author Xrw
     * @date 2022/7/20 9:56
     */
    Map<String, Object> checked(List<Integer> productIds, Integer isChecked);

    // lyx
    int update(Map<String, Integer> map);

    /**
     * 购物车页面：逻辑删除购物车商品
     *
     * @author Xrw
     * @date 2022/7/20 10:37
     */
    Map<String, Object> delete(List<Integer> productIds);

    /**
     * 商品页面：获取当前用户购物车中的商品总数（包含勾选和未勾选）
     *
     * @author Xrw
     * @date 2022/7/20 9:47
     */
    Integer goodsCount();

    // lyx
    Integer addWx(Map<String, Integer> map);
    //lyx
    int fastaddWx(Map<String, Integer> map);
}
