package com.cskaoyan.mapper.common;

import com.cskaoyan.bean.common.MarketCoupon;
import com.cskaoyan.bean.common.MarketCouponExample;
import com.cskaoyan.bean.wx.coupon.MyCouponListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketCouponMapper {
    long countByExample(MarketCouponExample example);

    int deleteByExample(MarketCouponExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketCoupon record);

    int insertSelective(MarketCoupon record);

    List<MarketCoupon> selectByExample(MarketCouponExample example);

    MarketCoupon selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketCoupon record, @Param("example") MarketCouponExample example);

    int updateByExample(@Param("record") MarketCoupon record, @Param("example") MarketCouponExample example);

    int updateByPrimaryKeySelective(MarketCoupon record);

    int updateByPrimaryKey(MarketCoupon record);


    /**
     * 根据用户id和优惠券状态查询myCouponList
     *
     * @param userId
     * @param status
     * @return java.util.List<com.cskaoyan.bean.wx.coupon.MyCouponListVO>
     * @author fanxing056
     * @date 2022/07/19 17:41
     */
    List<MyCouponListVO> selectUserCouponListByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") Integer status);
}