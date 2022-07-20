package com.cskaoyan.service.wx.cart;

import com.cskaoyan.bean.common.MarketCart;
import com.cskaoyan.bean.common.MarketCartExample;
import com.cskaoyan.bean.common.MarketUser;
import com.cskaoyan.exception.UnAuthException;
import com.cskaoyan.mapper.common.MarketCartMapper;
import com.cskaoyan.mapper.common.MarketGoodsProductMapper;
import com.cskaoyan.mapper.common.MarketGoodsSpecificationMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    MarketGoodsProductMapper productMapper;

    @Autowired
    MarketGoodsSpecificationMapper specificationMapper;

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
        criteria.andDeletedEqualTo(false);
        marketCartMapper.updateByPrimaryKeySelective(updateMarketCart);


        // {
        //     "productId": 255,
        //         "goodsId": 1181011,
        //         "number": 4,
        //         "id": 263
        // }

    }

    @Transactional
    @Override
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

    @Override
    public Integer add(Map<String, Integer> map) {
        return null;
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

    /**
     * 获取当前用户id
     *
     * @author Xrw
     * @date 2022/7/19 22:47
     */
    private Integer getMarketUserId() {
        // 获取userId
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        if (principals == null) {
            // 用户sessionId失效，重定向
            subject.logout();
            throw new UnAuthException();
        }
        MarketUser marketUser = (MarketUser) principals.getPrimaryPrincipal();
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
