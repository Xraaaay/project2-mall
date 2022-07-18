package com.cskaoyan.controller.promotion;

import com.cskaoyan.anno.ParamValidation;
import com.cskaoyan.bean.MarketCoupon;
import com.cskaoyan.bean.MarketCouponUser;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.service.promotion.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 优惠券
 *
 * @author fanxing056
 * @date 2022/07/16 16:00
 */

@RestController
@RequestMapping("/admin/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    /**
     * 条件查询优惠券信息及规则
     *
     * @param pageInfo
     * @param name
     * @param type
     * @param status
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 16:19
     */
    @GetMapping("/list")
    public BaseRespVo list(BasePageInfo pageInfo, String name, Short type, Short status) {

        CommonData<MarketCoupon> commonData = couponService.query(pageInfo, name, type, status);
        return BaseRespVo.ok(commonData);
    }

    /**
     * 添加优惠券
     *
     * @param coupon
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 16:52
     */
    @ParamValidation
    @PostMapping("/create")
    public BaseRespVo create(@RequestBody @Validated MarketCoupon coupon, BindingResult bindingResult) {

        couponService.create(coupon);
        return BaseRespVo.ok(coupon);
    }

    /**
     * 优惠券详情
     *
     * @param id
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 17:22
     */
    @GetMapping("/read")
    public BaseRespVo read(Integer id) {

        MarketCoupon coupon = couponService.read(id);
        if (coupon == null) {
            return BaseRespVo.invalidData();
        }
        return BaseRespVo.ok(coupon);
    }

    /**
     * 优惠券用户列表
     * 用户领取优惠券时应当维护coupon_user表
     * 优惠券用户使用表与订单表关联(订单id)，与优惠券表关联(start_time, end_time)
     *
     * @param pageInfo
     * @param couponId
     * @param userId
     * @param status
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 17:37
     */
    @GetMapping("/listuser")
    public BaseRespVo listUser(BasePageInfo pageInfo, Integer couponId, Integer userId, Short status) {

        CommonData<MarketCouponUser> commonData = couponService.listUser(pageInfo, couponId, userId, status);
        return BaseRespVo.ok(commonData);
    }

    /**
     * 修改优惠券信息
     *
     * @param coupon
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 18:14
     */
    @ParamValidation
    @PostMapping("/update")
    public BaseRespVo update(@RequestBody @Validated MarketCoupon coupon, BindingResult bindingResult) {

        couponService.update(coupon);
        return BaseRespVo.ok(coupon);
    }

    @PostMapping("/delete")
    public BaseRespVo delete(@RequestBody MarketCoupon coupon) {

        couponService.delete(coupon);
        return BaseRespVo.ok(null);
    }

}
