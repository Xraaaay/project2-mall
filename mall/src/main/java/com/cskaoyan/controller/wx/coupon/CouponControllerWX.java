package com.cskaoyan.controller.wx.coupon;

import com.cskaoyan.bean.common.MarketCoupon;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.service.admin.promotion.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 小程序优惠券
 *
 * @author fanxing056
 * @date 2022/07/18 20:57
 */

@RestController
@RequestMapping("/wx/coupon")
public class CouponControllerWX {

    @Autowired
    CouponService couponService;

    /**
     * 获取优惠券列表
     *
     * @param pageInfo
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/18 22:19
     */
    @GetMapping("/list")
    public BaseRespVo list(BasePageInfo pageInfo) {
        pageInfo.setSort("add_time");
        pageInfo.setOrder("desc");
        CommonData<MarketCoupon> commonData = couponService.query(pageInfo, null, null, null);
        return BaseRespVo.ok(commonData);
    }

    /**
     * 用户领取优惠券
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/18 22:25
     */
    @PostMapping("/receive")
    public BaseRespVo receive(@RequestBody  Map<String, Integer> map) {

        Integer couponId = map.get("couponId");

        int code = couponService.receive(couponId);
        return BaseRespVo.ok(null);
    }
}
