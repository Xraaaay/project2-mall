package com.cskaoyan.service.admin.promotion;

import com.cskaoyan.bean.common.MarketCoupon;
import com.cskaoyan.bean.common.MarketCouponUser;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;

/**
 * 优惠券管理
 *
 * @author fanxing056
 * @date 2022/07/16 16:04
 */

public interface CouponService {

    /**
     * 查询优惠券信息及规则
     *
     * @param basePageInfo
     * @param name
     * @param type
     * @param status
     * @return com.cskaoyan.bean.common.CommonData<com.cskaoyan.bean.common.MarketCoupon>
     * @author fanxing056
     * @date 2022/07/16 16:09
     */
    CommonData<MarketCoupon> query(BasePageInfo basePageInfo, String name, Short type, Short status);

    /**
     * 新增优惠券
     *
     * @param coupon
     * @return int
     * @author fanxing056
     * @date 2022/07/16 16:50
     */
    int create(MarketCoupon coupon);

    /**
     * 根据id获取优惠券详情
     *
     * @param id
     * @return com.cskaoyan.bean.common.MarketCoupon
     * @author fanxing056
     * @date 2022/07/16 17:16
     */
    MarketCoupon read(Integer id);

    /**
     * 根据优惠券id获取优惠券用户
     *
     * @param basePageInfo
     * @param couponId
     * @param userId
     * @param status
     * @return com.cskaoyan.bean.common.CommonData<com.cskaoyan.bean.common.MarketCouponUser>
     * @author fanxing056
     * @date 2022/07/16 17:27
     */
    CommonData<MarketCouponUser> listUser(BasePageInfo basePageInfo, Integer couponId, Integer userId, Short status);

    /**
     * 修改优惠券信息
     *
     * @param coupon
     * @return int
     * @author fanxing056
     * @date 2022/07/16 18:11
     */
    int update(MarketCoupon coupon);

    /**
     * 逻辑删除优惠券
     *
     * @param coupon
     * @return int
     * @author fanxing056
     * @date 2022/07/16 20:34
     */
    int delete(MarketCoupon coupon);

    /**
     * 用户领取优惠券
     *
     * @param couponId
     * @return int
     * @author fanxing056
     * @date 2022/07/18 22:31
     */
    int receive(Integer couponId);
}
