package com.cskaoyan.service.admin.promotion.impl;

import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.coupon.MyCouponListVO;
import com.cskaoyan.mapper.common.*;
import com.cskaoyan.service.admin.promotion.CouponService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
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

    public static final Integer RECEIVE_SUCCESS = 0;
    public static final Integer RECEIVE_NONE = 740;
    public static final Integer RECEIVE_YET = 741;


    @Autowired
    MarketCouponMapper couponMapper;

    @Autowired
    MarketCouponUserMapper couponUserMapper;

    @Autowired
    MarketCartMapper cartMapper;

    @Autowired
    MarketGoodsMapper goodsMapper;

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
        criteria.andDeletedEqualTo(false).andCouponIdEqualTo(couponId);
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

    @Transactional
    @Override
    public int receive(Integer couponId) {

        // 获取经过认证之后的用户信息
        Subject subject = SecurityUtils.getSubject();
        MarketUser user = (MarketUser) subject.getPrincipals().getPrimaryPrincipal();

        // 判断优惠券数量 0 表示无限量, -1表示领完了
        MarketCoupon coupon = couponMapper.selectByPrimaryKey(couponId);
        @Min(value = 0, message = "优惠券数量输入错误")
        Integer total = coupon.getTotal();
        if (total.equals(-1)) {
            return RECEIVE_NONE;
        }

        // 判断用户是否已经领取过
        // 此优惠券的限领数量大于0
        if (coupon.getLimit() > 0) {
            // 查找优惠券用户使用表获取当前用户领取过该优惠券的记录
            MarketCouponUserExample couponUserExample = new MarketCouponUserExample();
            MarketCouponUserExample.Criteria criteria = couponUserExample.createCriteria();
            criteria.andDeletedEqualTo(false).
                    andUserIdEqualTo(user.getId()).
                    andCouponIdEqualTo(couponId);
            List<MarketCouponUser> couponUserList = couponUserMapper.selectByExample(couponUserExample);
            // 已经领取过
            if (couponUserList.size() >= coupon.getLimit()) {
                return RECEIVE_YET;
            }
        }

        // 领取
        // 获取有效期
        Date fromDate;
        Date toDate;
        if (coupon.getTimeType().intValue() == 0) {
            fromDate = new Date();
            toDate = new Date(System.currentTimeMillis() + coupon.getDays() * 24 * 60 * 60 * 1000);
        } else {
            fromDate = coupon.getStartTime();
            toDate = coupon.getEndTime();
        }

        // 向优惠券用户使用表新增记录
        MarketCouponUser couponUser = new MarketCouponUser();
        couponUser.setUserId(user.getId());
        couponUser.setCouponId(couponId);
        couponUser.setStatus((short) 0);
        couponUser.setStartTime(fromDate);
        couponUser.setEndTime(toDate);
        couponUser.setAddTime(new Date());
        couponUser.setUpdateTime(new Date());
        couponUser.setDeleted(false);
        int affectCouponUser = couponUserMapper.insertSelective(couponUser);

        // 更新优惠券表
        MarketCoupon marketCoupon = new MarketCoupon();
        marketCoupon.setId(couponId);
        if (total.equals(1)) {
            marketCoupon.setTotal(-1);
        }
        if (total > 1) {
            marketCoupon.setTotal(coupon.getTotal() - 1);
        }
        marketCoupon.setUpdateTime(new Date());

        int affectCoupon = couponMapper.updateByPrimaryKeySelective(marketCoupon);

        return RECEIVE_SUCCESS;
    }

    @Override
    public CommonData<MyCouponListVO> myList(BasePageInfo pageInfo, Integer status) {

        // 获取经过认证之后的用户信息
        Subject subject = SecurityUtils.getSubject();
        MarketUser user = (MarketUser) subject.getPrincipals().getPrimaryPrincipal();

        // 开启分页
        PageHelper.startPage(pageInfo.getPage(), pageInfo.getLimit(), pageInfo.getSort() + " " + pageInfo.getOrder());

        List<MyCouponListVO> couponListVOList = couponMapper.selectUserCouponListByUserIdAndStatus(user.getId(), status);

        PageInfo<MyCouponListVO> listVOPageInfo = new PageInfo<>(couponListVOList);
        return CommonData.data(listVOPageInfo);
    }

    @Override
    public int exchange(String code) {

        // 根据兑换码获取优惠券id
        MarketCouponExample couponExample = new MarketCouponExample();
        MarketCouponExample.Criteria criteria = couponExample.createCriteria();
        criteria.andDeletedEqualTo(false).andCodeEqualTo(code);
        List<MarketCoupon> couponList = couponMapper.selectByExample(couponExample);

        if (couponList.size() == 1) {
            return receive(couponList.get(0).getId());
        }
        return RECEIVE_NONE;
    }

    // TODO:优惠券商品限制,当前全部是全场通用
    // 根据购物车中选中的商品来查询可用优惠券
    // 条件：状态为可用，商品限制类型
    @Override
    public CommonData<MyCouponListVO> selectList(BasePageInfo pageInfo, Integer cartId, Integer grouponRulesId) {

        // 获取经过认证之后的用户信息
        Subject subject = SecurityUtils.getSubject();
        MarketUser user = (MarketUser) subject.getPrincipals().getPrimaryPrincipal();

        BigDecimal amount = new BigDecimal(0);
        if (cartId == 0) {
            // 当前用户的所有商品
            // 根据用户id查询购物车商品表, 当前用户购物车中所有选中的商品
            MarketCartExample cartExample = new MarketCartExample();
            MarketCartExample.Criteria criteria = cartExample.createCriteria();
            criteria.andDeletedEqualTo(false).
                    andUserIdEqualTo(user.getId()).
                    andCheckedEqualTo(true);
            List<MarketCart> cartList = cartMapper.selectByExample(cartExample);

            // 所有选中的商品总价
            BigDecimal temp = new BigDecimal(0);
            for (MarketCart cart : cartList) {
                if (cart.getChecked()) {
                    amount = temp.add(cart.getPrice());
                    temp = amount;
                }
            }
        } else {

            // 直接下单页面查询可用优惠券
            // 只有一个商品

            // 根据cartId查询用户id&商品id&购物车中是否选择状态
            MarketCartExample cartExample = new MarketCartExample();
            MarketCartExample.Criteria criteria = cartExample.createCriteria();
            criteria.andDeletedEqualTo(false).andIdEqualTo(cartId);
            List<MarketCart> cartList = cartMapper.selectByExample(cartExample);
            if (cartList.size() == 0) {
                return null;
            }
            MarketCart cart = cartList.get(0);
            Integer userId = cart.getUserId();
            Integer goodsId = cart.getGoodsId();
            Boolean checked = cart.getChecked();
            // 商品货品的价格
            amount = cart.getPrice();

            // 根据商品id查询商品类目
        }

        // 当前用户所有可用优惠券
        CommonData<MyCouponListVO> commonData = myList(pageInfo, 0);
        // 过滤掉不满足最低消费的
        List<MyCouponListVO> couponListVOS = commonData.getList();
        for (int i = 0; i < couponListVOS.size(); i++) {
            if (couponListVOS.get(i).getMin().compareTo(amount) > 0) {
                couponListVOS.get(i).setAvailable(false);
            }
        }
        commonData.setList(couponListVOS);
        return commonData;
    }
}
