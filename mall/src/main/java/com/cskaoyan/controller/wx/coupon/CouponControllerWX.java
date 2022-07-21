package com.cskaoyan.controller.wx.coupon;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.common.MarketCoupon;
import com.cskaoyan.bean.wx.coupon.MyCouponListVO;
import com.cskaoyan.service.admin.promotion.CouponService;
import com.cskaoyan.service.admin.promotion.impl.CouponServiceImpl;
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

    private static final Short TYPE_COMMON = 0;
    private static final Short STATUS_NORMAL = 0;

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
        CommonData<MarketCoupon> commonData = couponService.query(pageInfo, null, TYPE_COMMON, STATUS_NORMAL);
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
    public BaseRespVo receive(@RequestBody Map<String, Integer> map) {

        Integer couponId = map.get("couponId");

        int code = couponService.receive(couponId);
        if (code == CouponServiceImpl.RECEIVE_NONE) {
            return BaseRespVo.invalidData("优惠券已领完");
        } else if (code == CouponServiceImpl.RECEIVE_YET) {
            return BaseRespVo.invalidData("优惠券已领取过");
        } else {
            return BaseRespVo.ok("成功");
        }
    }

    /**
     * 个人中心优惠券列表
     *
     * @param pageInfo
     * @param status
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/19 20:06
     */
    @GetMapping("/mylist")
    public BaseRespVo myList(BasePageInfo pageInfo, Integer status) {

        // 快过期的优惠券放在上方
        // market_coupon_user.end_time
        pageInfo.setSort("endTime");
        pageInfo.setOrder("ASC");
        CommonData<MyCouponListVO> commonData = couponService.myList(pageInfo, status);
        return BaseRespVo.ok(commonData);
    }

    @PostMapping("/exchange")
    public BaseRespVo exchange(@RequestBody Map<String, Object> map) {

        String code = (String) map.get("code");
        int status = couponService.exchange(code);
        if (status == CouponServiceImpl.RECEIVE_NONE) {
            return BaseRespVo.invalidData("优惠券不存在");
        } else if (status == CouponServiceImpl.RECEIVE_YET) {
            return BaseRespVo.invalidData("优惠券已兑换过");
        } else {
            return BaseRespVo.ok("兑换成功");
        }
    }

    /**
     * 下单页面选择优惠券
     *
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/20 15:43
     */
    @GetMapping("/selectlist")
    public BaseRespVo selectList(Integer cartId, Integer grouponRulesId) {

        BasePageInfo basePageInfo = new BasePageInfo();
        basePageInfo.setPage(1);
        basePageInfo.setLimit(8);
        // 面值大的放上面
        basePageInfo.setSort("discount");
        basePageInfo.setOrder("desc");
        CommonData<MyCouponListVO> commonData = couponService.selectList(basePageInfo, cartId, grouponRulesId);

        return BaseRespVo.ok(commonData);
    }
}
