package com.cskaoyan.service.wx.cart;


import com.cskaoyan.bean.admin.marketconfig.MarketExpreessVO;
import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.cart.CheckoutBo;
import com.cskaoyan.bean.wx.cart.CheckoutVo;
import com.cskaoyan.exception.UnAuthException;
import com.cskaoyan.mapper.common.*;
import com.cskaoyan.util.PrincipalUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
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
    public int update(Map<String, Integer> map) {
        int statusId = 0;
        Integer marketUserId = getMarketUserId();
        HashMap<String, Integer> hashMap = new HashMap<>(map);
        Integer productId = hashMap.get("productId");
        Integer goodsId = hashMap.get("goodsId");
        Integer number = hashMap.get("number");
        Integer id = hashMap.get("id");

        MarketGoodsProductExample example = new MarketGoodsProductExample();
        MarketGoodsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);

        MarketGoodsProduct marketGoodsProduct = marketGoodsProductMapper.selectByPrimaryKey(productId);

        short numbershort = number.shortValue();
        if (number > marketGoodsProduct.getNumber()) {
            statusId = 711;
            return statusId;
        }


        MarketCart updateMarketCart = new MarketCart();
        updateMarketCart.setId(id);
        updateMarketCart.setNumber(numbershort);

        MarketCartExample exampleCart = new MarketCartExample();
        MarketCartExample.Criteria criteriaCart = exampleCart.createCriteria();
        criteriaCart.andDeletedEqualTo(false)
                .andUserIdEqualTo(marketUserId);


        try {
            marketCartMapper.updateByPrimaryKeySelective(updateMarketCart);
        } catch (Exception e) {
            e.printStackTrace();
            return 404;
        }
        return 200;


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

        //cart 的总量
        MarketCartExample marketCartExample = new MarketCartExample();
        MarketCartExample.Criteria cartExampleCriteria = marketCartExample.createCriteria();
        cartExampleCriteria.andDeletedEqualTo(false)
                .andUserIdEqualTo(marketUserId);
        //算出购物车的总量
        Integer sumCartNumber = number;
        List<MarketCart> marketCarts = marketCartMapper.selectByExample(marketCartExample);
        for (MarketCart cart : marketCarts) {
            sumCartNumber +=  cart.getNumber();
        }

        MarketGoods marketGoods = marketGoodsMapper.selectByPrimaryKey(goodsId);

        MarketGoodsProduct marketGoodsProduct = marketGoodsProductMapper.selectByPrimaryKey(productId);
        //goodSn 强转成 String
        String goodsSn = Integer.toString(marketGoods.getId());
        //String[]转 String

        //判断库存量是否足够
        if (number > marketGoodsProduct.getNumber()) {
            statusId = 711;
            return statusId;
        }
        MarketCart marketCart = new MarketCart(null, marketUserId, goodsId, goodsSn,
                marketGoods.getName(), productId, marketGoodsProduct.getPrice(), numbershort, marketGoodsProduct.getSpecifications(),
                true, marketGoodsProduct.getUrl(), marketGoodsProduct.getAddTime(), marketGoodsProduct.getUpdateTime(), false);

        //判断 是否加入购物车的商品id已经存在于cart的数据库，如果存在只增加数量，如果不存在，再执行插入
        //同理，立即购买也是一样。但是立即购买要返回的是id  哪如果有这个商品的话，只能去插入
        //existGoodsId 为 1 存在相同 id   为0 不存在相同id
        Integer existGoodsId = isExistGoodsId(marketUserId, goodsId);
        //查询goodsId 哪一条
        MarketCartExample marketCartGoodsIdExample = new MarketCartExample();
        MarketCartExample.Criteria cartGoodsExampleCriteria = marketCartExample.createCriteria();
        cartGoodsExampleCriteria.andDeletedEqualTo(false)
                .andUserIdEqualTo(marketUserId)
                .andGoodsIdEqualTo(goodsId);
        List<MarketCart> marketGoodsCarts = marketCartMapper.selectByExample(marketCartGoodsIdExample);
        //查询出哪个相同goodsId 的商品   然后给他的数量付给新值
        //但是由于你自己知道查询出来只有一个 但是 系统不知道 还是给了个列表


        try {
            //存在相同existGoodsId 添加
            if (existGoodsId == 1) {
                Integer newNumber = marketGoodsCarts.get(0).getNumber() + number;
                short NewNumbershort = newNumber.shortValue();
                marketCarts.get(0).setNumber(NewNumbershort);
                marketCartMapper.updateByPrimaryKeySelective(marketGoodsCarts.get(0));
            } else {
                //不存在相同existGoodsId 插入
                marketCartMapper.insertSelective(marketCart);
            }
        }catch (Exception e) {
            statusId = 404;
            e.printStackTrace();
            return statusId;
        }
        // //如果成功返回值为 200
        // statusId = 200;
        return sumCartNumber;
    }

    private Integer isExistGoodsId(Integer marketUserId, Integer goodsId) {
        MarketCartExample marketCartExample = new MarketCartExample();
        MarketCartExample.Criteria cartExampleCriteria = marketCartExample.createCriteria();
        cartExampleCriteria.andDeletedEqualTo(false)
        .andUserIdEqualTo(marketUserId);
        //开始查询
        List<MarketCart> marketCarts = marketCartMapper.selectByExample(marketCartExample);
        for (MarketCart cart : marketCarts) {
            if (cart.getGoodsId() == goodsId) {
                //存在相同 goodsid
                return 1;
            }
        }
        //不存在相同id
       return 0;
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
        // 获取用户信息
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        if (principals==null) {
            // 用户未登录，购物车数量为0
            return 0;
        }
        MarketUser marketUser = (MarketUser) principals.getPrimaryPrincipal();

        MarketCartExample example = new MarketCartExample();
        example.createCriteria().andUserIdEqualTo(marketUser.getId())
                .andDeletedEqualTo(false);

        // 查询当前用户的所有购物车商品总数
        Integer goodsCount = marketCartMapper.selectGoodsCountByExample(example);

        if (goodsCount == null) {
            goodsCount = 0;
        }
        return goodsCount;
    }


    /**
     * lyx
     * 立即下单。 减少库存
     * @param map
     * @return
     */
    @Override
    public int fastaddWx(Map<String, Integer> map) {
        //定义一个状态值
        int statusId = 0;
        //用来获取是否为当前用户
        Integer marketUserId = getMarketUserId();
        //取出需要的参数 从HashMap中
        HashMap<String, Integer> hashMap = new HashMap<>(map);
        Integer productId = hashMap.get("productId");
        Integer goodsId = hashMap.get("goodsId");
        Integer number = hashMap.get("number");
        short numbershort = number.shortValue();
        //同增加购物车相同逻辑
        //product 的example
        MarketGoodsProductExample example = new MarketGoodsProductExample();
        MarketGoodsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        //goods 的example
        MarketGoodsExample goodsExample = new MarketGoodsExample();
        MarketGoodsExample.Criteria goodscriteria = goodsExample.createCriteria();
        goodscriteria.andDeletedEqualTo(false);
        //查询出goods的全部数据
        MarketGoods marketGoods = marketGoodsMapper.selectByPrimaryKey(goodsId);
        //查询出producat
        MarketGoodsProduct marketGoodsProduct = marketGoodsProductMapper.selectByPrimaryKey(productId);
        //goodSn 强转成 String 因为从商品查出来的goodsSn为Integer类型
        String goodsSn = Integer.toString(marketGoods.getId());
        //String[]转 String  理由同上

        //判断库存量是否足够
        if (number > marketGoodsProduct.getNumber()) {
            statusId = 711;
            return statusId;
        }
        //吧 cart数据库要用的值送给 marketCart
        MarketCart marketCart = new MarketCart(null, marketUserId, goodsId, goodsSn,
                marketGoods.getName(), productId, marketGoodsProduct.getPrice(), numbershort, marketGoodsProduct.getSpecifications(),
                true, marketGoodsProduct.getUrl(), marketGoodsProduct.getAddTime(), marketGoodsProduct.getUpdateTime(), false);
        //插入数据，如果成功就返回200，异常404 数量不足返回 711

        try {
            marketCartMapper.insertSelective(marketCart);
        } catch (Exception e) {
            statusId = 404;
            e.printStackTrace();
            return statusId;
        }

        return  marketCart.getId();

    }





    /**
     * 获取当前用户的所有商品信息
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

    /**
     * 获取当前用户的选中商品信息checked
     *
     * @author Xrw
     * @date 2022/7/21 9:53
     */
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
