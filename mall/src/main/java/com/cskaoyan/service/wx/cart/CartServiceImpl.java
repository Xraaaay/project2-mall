package com.cskaoyan.service.wx.cart;

import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.cart.CartTotalEntity;
import com.cskaoyan.bean.wx.cart.WxCartVO;
import com.cskaoyan.exception.InvalidDataException;
import com.cskaoyan.exception.UnAuthException;
import com.cskaoyan.mapper.common.MarketCartMapper;
import com.cskaoyan.mapper.common.MarketCategoryMapper;
import com.cskaoyan.mapper.common.MarketGoodsMapper;
import com.cskaoyan.mapper.common.MarketGoodsProductMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        MarketUser marketUser = getMarketUser();
        // 修改勾选状态
        for (Integer productId : productIds) {
            MarketCart marketCart = new MarketCart();
            marketCart.setUserId(marketUser.getId());
            marketCart.setProductId(productId);
            if (isChecked == 0) {
                marketCart.setChecked(false);
            } else {
                marketCart.setChecked(true);
            }

            MarketCartExample example = new MarketCartExample();
            example.createCriteria().andUserIdEqualTo(marketUser.getId())
                    .andProductIdEqualTo(productId);
            marketCartMapper.updateByExampleSelective(marketCart, example);
        }

        List<MarketCart> cartList = getCartList();
        Map<String, Object> cartTotal = getCartTotal(cartList);

        Map<String, Object> index = new HashMap<>();
        index.put("cartList", cartList);
        index.put("cartTotal", cartTotal);

        return index;
        // WxCartVO wxCartVO = new WxCartVO();
        //
        // MarketCartExample example = new MarketCartExample();
        // MarketCartExample.Criteria criteria = example.createCriteria();
        // criteria.andDeletedEqualTo(false);
        //
        // List<MarketCart> marketCarts = marketCartMapper.selectByExample(example);
        // for (MarketCart marketCart : marketCarts) {
        //     for (Integer productId : productIds) {
        //         if (productId.equals(marketCart.getProductId())) {
        //             //改怎麽解決 boolean的传参问题
        //             MarketCart updateMarketCart = new MarketCart();
        //             updateMarketCart.setId(marketCart.getId());
        //
        //             if (isChecked == 1) {
        //                 updateMarketCart.setChecked(true);
        //             } else {
        //                 updateMarketCart.setChecked(false);
        //             }
        //             // marketCartMapper.updateByExample(marketCart, example);
        //             marketCartMapper.updateByPrimaryKeySelective(updateMarketCart);
        //         }
        //     }
        //
        //
        // }
        // //感觉 我写的好麻烦啊，先全部查，再改，再全部查。   能不能直接根据id插入。 应该可以老师也讲过，但是我不知道什么情况下是不
        // //不去省略未改变的，
        // List<MarketCart> marketCartsAgain = marketCartMapper.selectByExample(example);
        // //开始去求cartTotal
        // CartTotalEntity cartTotal = marketCartMapper.selectCartTotal();
        //
        // wxCartVO.setCartList(marketCartsAgain);
        // wxCartVO.setCartTotal(cartTotal);
        //
        // return wxCartVO;
    }

    @Override
    public Integer goodsCount() {
        MarketUser marketUser = getMarketUser();
        Integer goodscount = marketCartMapper.selectGoodscount(marketUser.getId());
        return goodscount;
    }

    @Override
    public void update(Map<String, Integer> map) {
        MarketUser marketUser = getMarketUser();
        HashMap<String, Integer> hashMap = new HashMap<>(map);
        Integer productId = hashMap.get("productId");
        Integer goodsId = hashMap.get("goodsId");
        Integer number = hashMap.get("number");
        Integer id = hashMap.get("id");

        short numbershort = number.shortValue();

        //


        MarketCart updateMarketCart = new MarketCart();
        updateMarketCart.setId(id);
        updateMarketCart.setNumber(numbershort);

        MarketCartExample example = new MarketCartExample();
        MarketCartExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false)
        .andUserIdEqualTo(marketUser.getId());
        marketCartMapper.updateByPrimaryKeySelective(updateMarketCart);


    }

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
        MarketUser marketUser = getMarketUser();

        HashMap<String, Integer> hashMap = new HashMap<>(map);
        Integer productId = hashMap.get("productId");
        Integer goodsId = hashMap.get("goodsId");
        Integer number =  hashMap.get("number");
        short numbershort = number.shortValue();

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
        String getSpecifications= Arrays.toString(marketGoodsProduct.getSpecifications());
        //判断库存量是否足够
        if (number > marketGoodsProduct.getNumber()) {
            statusId = 711;
            return statusId;
        }





        MarketCart marketCart = new MarketCart(null, marketUser.getId(), goodsId, goodsSn,
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

    @Override
    public WxCartVO delete(List<Integer> productIds) {
        WxCartVO wxCartVO = new WxCartVO();
        MarketUser marketUser = getMarketUser();


        MarketCartExample example = new MarketCartExample();
        MarketCartExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false)
                .andUserIdEqualTo(marketUser.getId());
        List<MarketCart> marketCarts = marketCartMapper.selectByExample(example);
        for (MarketCart marketCart : marketCarts) {
            for (Integer productId : productIds) {
                if (productId.equals(marketCart.getProductId())) {
                    //改怎麽解決 boolean的传参问题
                    MarketCart updateMarketCart = new MarketCart();

                    updateMarketCart.setId(marketCart.getId());
                    updateMarketCart.setDeleted(true);

                    // marketCartMapper.updateByExample(marketCart, example);
                    marketCartMapper.updateByPrimaryKeySelective(updateMarketCart);
                }

            }
        }
        List<MarketCart> marketCartsAgain = marketCartMapper.selectByExample(example);
        //开始去求cartTotal
        CartTotalEntity cartTotal = marketCartMapper.selectCartTotal();

        wxCartVO.setCartList(marketCartsAgain);
        wxCartVO.setCartTotal(cartTotal);

        return wxCartVO;


    }

    /**
     * 获取当前用户的所有订单信息
     *
     * @author Xrw
     * @date 2022/7/19 22:44
     */
    private List<MarketCart> getCartList() {
        MarketUser user = getMarketUser();

        MarketCartExample example = new MarketCartExample();
        MarketCartExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false)
                .andUserIdEqualTo(user.getId());
        // 查询用户所有订单
        return marketCartMapper.selectByExample(example);
    }

    /**
     * 获取当前用户
     *
     * @author Xrw
     * @date 2022/7/19 22:47
     */
    private MarketUser getMarketUser() {
        // 获取userId
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        if (principals == null) {
            // 用户sessionId失效，重定向
            subject.logout();
            throw new UnAuthException();
        }
        return (MarketUser) principals.getPrimaryPrincipal();
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
