package com.cskaoyan.service.wx.cart;


import com.cskaoyan.bean.admin.marketconfig.MarketExpreessVO;
import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.cart.CheckoutBo;
import com.cskaoyan.bean.wx.cart.CheckoutVo;
import com.cskaoyan.mapper.common.*;
import com.cskaoyan.util.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author lyx
 * @since 2022/07/19 10:46
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    MarketCartMapper marketCartMapper;
    @Autowired
    MarketGoodsProductMapper marketGoodsProductMapper;
    @Autowired
    MarketGoodsMapper marketGoodsMapper;
    @Autowired
    MarketCouponMapper couponMapper;
    @Autowired
    MarketCouponUserMapper couponUserMapper;
    @Autowired
    MarketAddressMapper addressMapper;
    @Autowired
    MarketSystemMapper systemMapper;

    @Override
    public Map<String, Object> index() {
        List<MarketCart> cartList = getCartList();
        Map<String, Object> cartTotal = getCartTotal(cartList);

        Map<String, Object> index = new HashMap<>();
        index.put("cartList", cartList);
        index.put("cartTotal", cartTotal);

        return index;
    }

    @Transactional
    @Override
    public Map<String, Object> checked(List<Integer> productIds, Integer isChecked) {
        Integer userId = getMarketUserId();
        // 修改勾选状态
        for (Integer productId : productIds) {
            MarketCart marketCart = new MarketCart();
            if (isChecked == 0) {
                marketCart.setChecked(false);
            } else {
                marketCart.setChecked(true);
            }
            marketCart.setUpdateTime(new Date());

            MarketCartExample example = new MarketCartExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andProductIdEqualTo(productId)
                    .andDeletedEqualTo(false);
            marketCartMapper.updateByExampleSelective(marketCart, example);
        }

        return index();
    }

    @Override
    public void update(Map<String, Integer> map) {
        Integer marketUserId = getMarketUserId();
        HashMap<String, Integer> hashMap = new HashMap<>(map);
        Integer productId = hashMap.get("productId");
        Integer goodsId = hashMap.get("goodsId");
        Integer number = hashMap.get("number");
        Integer id = hashMap.get("id");

        short numbershort = number.shortValue();


        MarketCart updateMarketCart = new MarketCart();
        updateMarketCart.setId(id);
        updateMarketCart.setNumber(numbershort);

        MarketCartExample example = new MarketCartExample();
        MarketCartExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false)
                .andUserIdEqualTo(marketUserId);
        marketCartMapper.updateByPrimaryKeySelective(updateMarketCart);


        // {
        //     "productId": 255,
        //         "goodsId": 1181011,
        //         "number": 4,
        //         "id": 263
        // }

    }

    @Transactional
    /**
     * lyx
     * 增加到数据库中，要链接商品表和产品表， 根据商品id 和产品id 求出 两个表的数据
     * 再插入到购物车类中。 然后使用插入方法给数据库增加数据
     * @param map
     * @return
     */
    @Override
    public Integer addWx(Map<String, Integer> map) {
        int statusId = 0;
        Integer marketUserId = getMarketUserId();

        HashMap<String, Integer> hashMap = new HashMap<>(map);
        Integer productId = hashMap.get("productId");
        Integer goodsId = hashMap.get("goodsId");
        Integer number = hashMap.get("number");
        short numbershort = number.shortValue();

       /* MarketCartExample example = new MarketCartExample();
        example.createCriteria().andUserIdEqualTo(marketUserId)
                .andProductIdEqualTo(productId);
        marketCartMapper.updateByExampleSelective(cart, example);*/
        MarketGoodsProductExample example = new MarketGoodsProductExample();
        MarketGoodsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);

        MarketGoodsExample goodsExample = new MarketGoodsExample();
        MarketGoodsExample.Criteria goodscriteria = goodsExample.createCriteria();
        goodscriteria.andDeletedEqualTo(false);

        MarketGoods marketGoods = marketGoodsMapper.selectByPrimaryKey(goodsId);

        MarketGoodsProduct marketGoodsProduct = marketGoodsProductMapper.selectByPrimaryKey(productId);
        //goodSn 强转成 String
        String goodsSn = Integer.toString(marketGoods.getGoodsSn());
        //String[]转 String
        String getSpecifications = Arrays.toString(marketGoodsProduct.getSpecifications());
        //判断库存量是否足够
        if (number > marketGoodsProduct.getNumber()) {
            statusId = 711;
            return statusId;
        }
        MarketCart marketCart = new MarketCart(null, marketUserId, goodsId, goodsSn,
                marketGoods.getName(), productId, marketGoodsProduct.getPrice(), numbershort, getSpecifications,
                true, marketGoodsProduct.getUrl(), marketGoodsProduct.getAddTime(), marketGoodsProduct.getUpdateTime(), false);

        try {
            marketCartMapper.insertSelective(marketCart);
        } catch (Exception e) {
            statusId = 404;
            e.printStackTrace();
            return statusId;
        }
        //如果成功返回值为 200
        statusId = 200;
        return statusId;
    }

    @Transactional
    @Override
    public CheckoutVo checkout(CheckoutBo checkoutBo) {
        Integer grouponRulesId = checkoutBo.getGrouponRulesId();
        Integer cartId = checkoutBo.getCartId();
        Integer couponId = checkoutBo.getCouponId();
        Integer userCouponId = checkoutBo.getUserCouponId();
        Integer addressId = checkoutBo.getAddressId();

        // 商品信息
        List<MarketCart> checkedGoodsList = getCheckedCartList();
        Map<String, Object> cartTotal = getCartTotal(checkedGoodsList);
        BigDecimal goodsTotalPrice = (BigDecimal) cartTotal.get("checkedGoodsAmount");

        // 地址
        MarketAddress checkedAddress = addressMapper.selectByPrimaryKey(addressId);

        // 运费
        String freightMinStr = systemMapper.selectValueByName("market_express_freight_min");
        String freightValueStr = systemMapper.selectValueByName("market_express_freight_value");
        BigDecimal freightMin = new BigDecimal(freightMinStr);
        BigDecimal freightValue = new BigDecimal(freightValueStr);
        BigDecimal freightPrice = new BigDecimal(0);
        if (freightMin.compareTo(goodsTotalPrice) > 0) {
            freightPrice = freightValue;
        }

        // 优惠券数量
        Integer userId = getMarketUserId();
        MarketCouponUserExample example = new MarketCouponUserExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStatusEqualTo((short) 0)
                .andDeletedEqualTo(false);
        List<MarketCouponUser> marketCouponUsers = couponUserMapper.selectByExample(example);
        Integer availableCouponLength = marketCouponUsers.size();

        // 优惠信息
        MarketCoupon coupon = couponMapper.selectByPrimaryKey(couponId);
        BigDecimal couponPrice = new BigDecimal(0);
        if (coupon != null) {
            for (MarketCouponUser couponUser : marketCouponUsers) {
                if (couponUser.getCouponId().equals(couponId)) {
                    couponPrice = coupon.getDiscount();
                }
            }
        }

        // 商品总价
        BigDecimal actualPrice = goodsTotalPrice.subtract(couponPrice).add(freightPrice);
        BigDecimal orderTotalPrice = actualPrice;

        return new CheckoutVo(grouponRulesId, actualPrice, orderTotalPrice, cartId,
                userCouponId, couponId, goodsTotalPrice, addressId, 0, checkedAddress,
                couponPrice, availableCouponLength, freightPrice, checkedGoodsList);
    }


    public Map<String, Object> delete(List<Integer> productIds) {
        Integer userId = getMarketUserId();
        // 逻辑删除，修改deleted字段为true
        for (Integer productId : productIds) {
            MarketCart cart = new MarketCart();
            cart.setDeleted(true);

            MarketCartExample example = new MarketCartExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andProductIdEqualTo(productId);
            marketCartMapper.updateByExampleSelective(cart, example);
        }

        return index();
    }

    @Transactional
    @Override
    public Integer goodsCount() {
        Integer userId = getMarketUserId();
        MarketCartExample example = new MarketCartExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andDeletedEqualTo(false);

        // 查询当前用户的所有购物车商品总数
        Integer goodsCount = marketCartMapper.selectGoodsCountByExample(example);

        if (goodsCount == null) {
            goodsCount = 0;
        }
        return goodsCount;
    }

    /**
     * 获取当前用户的所有订单信息
     *
     * @author Xrw
     * @date 2022/7/19 22:44
     */
    private List<MarketCart> getCartList() {
        Integer userId = getMarketUserId();

        MarketCartExample example = new MarketCartExample();
        MarketCartExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false)
                .andUserIdEqualTo(userId);
        // 查询用户所有订单
        return marketCartMapper.selectByExample(example);
    }

    private List<MarketCart> getCheckedCartList() {
        Integer userId = getMarketUserId();

        MarketCartExample example = new MarketCartExample();
        MarketCartExample.Criteria criteria = example.createCriteria();
        criteria.andCheckedEqualTo(true)
                .andDeletedEqualTo(false)
                .andUserIdEqualTo(userId);
        // 查询用户所有订单
        return marketCartMapper.selectByExample(example);
    }

    /**
     * 获取当前用户id
     *
     * @author Xrw
     * @date 2022/7/19 22:47
     */
    private Integer getMarketUserId() {
        MarketUser marketUser = PrincipalUtil.getUserInfo();
        return marketUser.getId();
    }

    /**
     * 获取cartTotal对象，包括
     * goodsCount 订单数量
     * checkedGoodsCount 已选订单数量
     * goodsAmount 订单额
     * checkedGoodsAmount 已选订单额
     *
     * @author Xrw
     * @date 2022/7/19 22:44
     */
    private Map<String, Object> getCartTotal(List<MarketCart> cartList) {
        int goodsCount = 0;
        int checkedGoodsCount = 0;
        BigDecimal goodsAmount = new BigDecimal(0);
        BigDecimal checkedGoodsAmount = new BigDecimal(0);
        for (MarketCart cart : cartList) {
            goodsCount += cart.getNumber();
            goodsAmount = goodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            if (cart.getChecked()) {
                checkedGoodsCount += cart.getNumber();
                checkedGoodsAmount = checkedGoodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }
        Map<String, Object> cartTotal = new HashMap<>();
        cartTotal.put("goodsCount", goodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("goodsAmount", goodsAmount);
        return cartTotal;
    }
}
