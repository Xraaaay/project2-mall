package com.cskaoyan.service.wx.cart;

import com.cskaoyan.bean.common.MarketCart;
import com.cskaoyan.bean.common.MarketCartExample;
import com.cskaoyan.bean.wx.cart.CartTotalEntity;
import com.cskaoyan.bean.wx.cart.WxCartVO;
import com.cskaoyan.mapper.common.MarketCartMapper;
import com.cskaoyan.mapper.common.MarketCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Integer goodsCount() {

        Integer goodscount = marketCartMapper.selectGoodscount();
        return goodscount;
    }



    @Override
    public WxCartVO index() {
        WxCartVO wxCartVO = new WxCartVO();
        MarketCartExample example = new MarketCartExample();
        MarketCartExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<MarketCart> marketCartsAgain = marketCartMapper.selectByExample(example);
        //开始去求cartTotal
        CartTotalEntity cartTotal = marketCartMapper.selectCartTotal();

        wxCartVO.setCartList(marketCartsAgain);
        wxCartVO.setCartTotal(cartTotal);
        return wxCartVO;
    }

    @Override
    public void update(Map<String, Integer> map) {
        HashMap<String, Integer> hashMap = new HashMap<>(map);
        Integer productId = hashMap.get("productId");
        Integer goodsId = hashMap.get("goodsId");
      Integer number =  hashMap.get("number");
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

    @Override
    public Integer addWx(Map<String, Integer> map) {
     /*   HashMap<String, Integer> hashMap = new HashMap<>(map);
        Integer productId = hashMap.get("productId");
        Integer goodsId = hashMap.get("goodsId");
        Integer number =  hashMap.get("number");
        short numbershort = number.shortValue();

        MarketCartExample example = new MarketCartExample();
        MarketCartExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        marketCartMapper.insert(updateMarketCart);
*/


        return null;
    }

    @Override
    public WxCartVO delete(List<Integer> productIds) {
        WxCartVO wxCartVO = new WxCartVO();

        MarketCartExample example = new MarketCartExample();
        MarketCartExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
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



    @Override
    public WxCartVO checked(List<Integer> productIds, Integer isChecked) {
        WxCartVO wxCartVO = new WxCartVO();

        MarketCartExample example = new MarketCartExample();
        MarketCartExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);

        List<MarketCart> marketCarts = marketCartMapper.selectByExample(example);
        for (MarketCart marketCart : marketCarts) {
            for (Integer productId : productIds) {
                if (productId.equals(marketCart.getProductId())) {
                    //改怎麽解決 boolean的传参问题
                    MarketCart updateMarketCart = new MarketCart();
                    updateMarketCart.setId(marketCart.getId());

                    if (isChecked == 1) {
                        updateMarketCart.setChecked(true);
                    }else {
                        updateMarketCart.setChecked(false);
                    }
                    // marketCartMapper.updateByExample(marketCart, example);
                    marketCartMapper.updateByPrimaryKeySelective(updateMarketCart);
                }
            }


        }
        //感觉 我写的好麻烦啊，先全部查，再改，再全部查。   能不能直接根据id插入。 应该可以老师也讲过，但是我不知道什么情况下是不
        //不去省略未改变的，
        List<MarketCart> marketCartsAgain = marketCartMapper.selectByExample(example);
        //开始去求cartTotal
        CartTotalEntity cartTotal = marketCartMapper.selectCartTotal();

        wxCartVO.setCartList(marketCartsAgain);
        wxCartVO.setCartTotal(cartTotal);

        return wxCartVO;
    }


}
