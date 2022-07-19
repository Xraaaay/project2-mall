package com.cskaoyan.service.admin.promotion.impl;

import com.cskaoyan.bean.common.MarketCoupon;
import com.cskaoyan.bean.common.MarketCouponExample;
import com.cskaoyan.bean.common.MarketCouponUser;
import com.cskaoyan.bean.common.MarketCouponUserExample;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.mapper.common.MarketCouponMapper;
import com.cskaoyan.mapper.common.MarketCouponUserMapper;
import com.cskaoyan.service.admin.promotion.CouponService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 优惠券管理
 *
 * @author fanxing056
 * @date 2022/07/16 16:08
 */

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    MarketCouponMapper couponMapper;

    @Autowired
    MarketCouponUserMapper couponUserMapper;

    @Override
    public CommonData<MarketCoupon> query(BasePageInfo basePageInfo, String name, Short type, Short status) {

        // 开启分页
        PageHelper.startPage(basePageInfo.getPage(), basePageInfo.getLimit());

        MarketCouponExample marketCouponExample = new MarketCouponExample();
        MarketCouponExample.Criteria criteria = marketCouponExample.createCriteria();
        marketCouponExample.setOrderByClause(basePageInfo.getSort() + " " + basePageInfo.getOrder());

        // 添加条件
        criteria.andDeletedEqualTo(false);
        if (!StringUtil.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }

        List<MarketCoupon> marketCouponList = couponMapper.selectByExample(marketCouponExample);

        PageInfo<MarketCoupon> pageInfo = new PageInfo<>(marketCouponList);

        return CommonData.data(pageInfo);
    }

    // TODO: 商品限制类型，商品限制值， 优惠券兑换码
    @Transactional
    @Override
    public int create(MarketCoupon coupon) {

        coupon.setAddTime(new Date());
        coupon.setUpdateTime(new Date());

        int affect = couponMapper.insertSelective(coupon);
        return affect;
    }

    @Override
    public MarketCoupon read(Integer id) {

        MarketCoupon coupon = couponMapper.selectByPrimaryKey(id);
        return coupon;
    }

    // TODO:优化重复代码
    @Override
    public CommonData<MarketCouponUser> listUser(BasePageInfo basePageInfo, Integer couponId, Integer userId, Short status) {

        // 开启分页
        PageHelper.startPage(basePageInfo.getPage(), basePageInfo.getLimit());

        // 添加条件
        MarketCouponUserExample marketCouponUserExample = new MarketCouponUserExample();
        MarketCouponUserExample.Criteria criteria = marketCouponUserExample.createCriteria();
        marketCouponUserExample.setOrderByClause(basePageInfo.getSort() + " " + basePageInfo.getOrder());
        criteria.andDeletedEqualTo(false);
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        List<MarketCouponUser> marketCouponUserList = couponUserMapper.selectByExample(marketCouponUserExample);

        PageInfo<MarketCouponUser> pageInfo = new PageInfo<>(marketCouponUserList);

        return CommonData.data(pageInfo);
    }

    @Override
    public int update(MarketCoupon coupon) {

        coupon.setUpdateTime(new Date());
        int affect = couponMapper.updateByPrimaryKeySelective(coupon);
        return affect;
    }

    @Override
    public int delete(MarketCoupon coupon) {

        coupon.setDeleted(true);
        int affect = couponMapper.updateByPrimaryKeySelective(coupon);
        return affect;
    }

    // TODO
    @Override
    public int receive(Integer couponId) {


        return 0;
    }
}
