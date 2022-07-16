package com.cskaoyan.controller.promotion;

import com.cskaoyan.bean.MarketCoupon;
import com.cskaoyan.bean.MarketCouponUser;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.service.promotion.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param page
     * @param limit
     * @param name
     * @param type
     * @param status
     * @param sort
     * @param order
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 16:19
     */
    @GetMapping("/list")
    public BaseRespVo list(Integer page, Integer limit, String name,
                           Short type, Short status, String sort, String order) {

        CommonData<MarketCoupon> commonData = couponService.query(page, limit, name, type, status, sort, order);

        return BaseRespVo.ok(commonData);
    }


    /**
     * 添加优惠券
     * TODO:商品限制范围
     *
     * @param coupon
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 16:52
     */
    @PostMapping("/create")
    public BaseRespVo create(@RequestBody MarketCoupon coupon) {

        try {
            couponService.create(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseRespVo.invalidData();
        }
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
     * TODO:测试， 暂无数据，优惠券用户使用表与订单表关联(订单id)，与优惠券表关联(start_time, end_time)
     *
     * @param page
     * @param limit
     * @param couponId
     * @param userId
     * @param status
     * @param sort
     * @param order
     * @return com.cskaoyan.bean.BaseRespVo
     * @author fanxing056
     * @date 2022/07/16 17:37
     */
    @GetMapping("/listuser")
    public BaseRespVo listUser(Integer page, Integer limit, Integer couponId, Integer userId,
                               Short status, String sort, String order) {

        CommonData<MarketCouponUser> commonData = couponService.listUser(page, limit, couponId, userId, status, sort, order);

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
    @PostMapping("/update")
    public BaseRespVo update(@RequestBody MarketCoupon coupon) {

        try {
            couponService.update(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseRespVo.invalidData();
        }

        return BaseRespVo.ok(coupon);
    }

    @PostMapping("/delete")
    public BaseRespVo delete(@RequestBody MarketCoupon coupon) {

        couponService.delete(coupon);
        return BaseRespVo.ok(null);
    }

}
