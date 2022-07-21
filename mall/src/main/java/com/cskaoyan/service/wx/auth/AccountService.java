package com.cskaoyan.service.wx.auth;

import com.cskaoyan.bean.common.MarketCoupon;

import java.util.List;
import java.util.Map;

/**
 * 用户注册与账号密码重置
 *
 * @author fanxing056
 * @date 2022/07/19 20:29
 */

public interface AccountService {

    /**
     * 发送验证码
     *
     * @param mobile
     * @return int
     * @author fanxing056
     * @date 2022/07/19 20:32
     */
    int regCaptcha(String mobile);

    /**
     * 用户注册
     *
     * @param map
     * @return java.util.List<com.cskaoyan.bean.common.MarketCoupon> 注册赠券列表
     * @author fanxing056
     * @date 2022/07/21 19:57
     */
    List<MarketCoupon> register(Map<String, String> map) throws Exception;

    /**
     * 用户重置密码
     *
     * @param map
     * @return int
     * @author fanxing056
     * @date 2022/07/20 12:05
     */
    int reset(Map<String, String> map) throws Exception;
}
