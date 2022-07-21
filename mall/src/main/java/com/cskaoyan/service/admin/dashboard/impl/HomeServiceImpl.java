package com.cskaoyan.service.admin.dashboard.impl;

import com.cskaoyan.bean.common.*;
import com.cskaoyan.mapper.common.*;
import com.cskaoyan.service.admin.dashboard.HomeService;
import com.github.pagehelper.PageHelper;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小程序首页
 * TODO:优化代码！！
 *
 * @author fanxing056
 * @date 2022/07/18 17:04
 */

@Service
public class HomeServiceImpl implements HomeService {

    private static final int pageSize4 = 4;
    private static final int pageSize3 = 3;
    private static final int pageSize6 = 6;
    private static final int pageSize12 = 12;

    @Autowired
    MarketGoodsMapper goodsMapper;
    @Autowired
    MarketCouponMapper couponMapper;
    @Autowired
    MarketCategoryMapper categoryMapper;
    @Autowired
    MarketAdMapper adMapper;
    @Autowired
    MarketBrandMapper brandMapper;
    @Autowired
    MarketTopicMapper topicMapper;

    @Override
    public Map<String, Object> index() {

        // 最新商品
        PageHelper.startPage(1, pageSize4);
        MarketGoodsExample goodsExample = new MarketGoodsExample();
        MarketGoodsExample.Criteria criteria = goodsExample.createCriteria();
        goodsExample.setOrderByClause("add_time desc");
        criteria.andDeletedEqualTo(false).andIsNewEqualTo(true);
        List<MarketGoods> newGoodsList = goodsMapper.selectByExample(goodsExample);

        // 最热商品
        PageHelper.startPage(1, pageSize3);
        MarketGoodsExample goodsExample2 = new MarketGoodsExample();
        MarketGoodsExample.Criteria criteria2 = goodsExample.createCriteria();
        goodsExample2.setOrderByClause("add_time desc");
        criteria2.andDeletedEqualTo(false).andIsHotEqualTo(true);
        List<MarketGoods> hotGoodsList = goodsMapper.selectByExample(goodsExample);

        // 优惠券列表
        PageHelper.startPage(1, pageSize3);
        MarketCouponExample couponExample = new MarketCouponExample();
        MarketCouponExample.Criteria couponExampleCriteria = couponExample.createCriteria();
        couponExample.setOrderByClause("add_time desc");
        couponExampleCriteria.andDeletedEqualTo(false);
        List<MarketCoupon> couponList = couponMapper.selectByExample(null);

        // 类目列表
        MarketCategoryExample categoryExample = new MarketCategoryExample();
        MarketCategoryExample.Criteria categoryExampleCriteria = categoryExample.createCriteria();
        categoryExample.setOrderByClause("add_time desc");
        categoryExampleCriteria.andDeletedEqualTo(false).andPidEqualTo(0);
        List<MarketCategory> pCategoryList = categoryMapper.selectByExample(categoryExample);

        // 广告列表
        PageHelper.startPage(1, 15);
        MarketAdExample adExample = new MarketAdExample();
        MarketAdExample.Criteria adExampleCriteria = adExample.createCriteria();
        adExample.setOrderByClause("add_time desc");
        adExampleCriteria.andDeletedEqualTo(false);
        List<MarketAd> adList = adMapper.selectByExample(adExample);

        // 品牌商列表
        PageHelper.startPage(1, pageSize6);
        MarketBrandExample brandExample = new MarketBrandExample();
        MarketBrandExample.Criteria brandExampleCriteria = brandExample.createCriteria();
        brandExample.setOrderByClause("id asc");
        brandExampleCriteria.andDeletedEqualTo(false);
        List<MarketBrand> brandList = brandMapper.selectByExample(brandExample);

        // 专题列表
        PageHelper.startPage(1, pageSize6);
        MarketTopicExample topicExample = new MarketTopicExample();
        MarketTopicExample.Criteria topicExampleCriteria = topicExample.createCriteria();
        topicExample.setOrderByClause("add_time desc");
        topicExampleCriteria.andDeletedEqualTo(false);
        List<MarketTopic> topicList = topicMapper.selectByExample(topicExample);

        // floorGoodsList
        List<Map<String, Object>> floorGoodsList = new ArrayList<>();
        int count = 0;
        for (MarketCategory pCategory : pCategoryList) {
            Map<String, Object> floorGoods = new HashMap<>();
            floorGoods.put("name", pCategory.getName());
            floorGoods.put("id", pCategory.getId());
            // 子类目列表
            MarketCategoryExample categoryExample2 = new MarketCategoryExample();
            MarketCategoryExample.Criteria categoryExampleCriteria2 = categoryExample2.createCriteria();
            categoryExample2.setOrderByClause("add_time desc");
            categoryExampleCriteria2.andDeletedEqualTo(false).andPidEqualTo(pCategory.getId());
            List<MarketCategory> categoryList = categoryMapper.selectByExample(categoryExample2);

            int temp = 0;
            List<MarketGoods> marketGoods = new ArrayList<>();
            for (MarketCategory category : categoryList) {
                // 每个子类目取商品,取到6为止
                if (temp >= pageSize6) {
                    break;
                }
                // 根据类目id查询商品
                MarketGoodsExample goodsExample3 = new MarketGoodsExample();
                MarketGoodsExample.Criteria criteria3 = goodsExample3.createCriteria();
                goodsExample3.setOrderByClause("id asc");
                criteria3.andDeletedEqualTo(false).
                        andCategoryIdEqualTo(category.getId());
                List<MarketGoods> goodsList = goodsMapper.selectByExample(goodsExample3);

                for (MarketGoods goods : goodsList) {
                    if (temp >= pageSize6) {
                        break;
                    }
                    marketGoods.add(goods);
                    temp++;
                }
            }
            floorGoods.put("goodsList", marketGoods);
            floorGoodsList.add(floorGoods);
            if (count++ > pageSize3) {
                break;
            }
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("newGoodsList", newGoodsList);
        map.put("couponList", couponList);
        map.put("channel", pCategoryList);
        map.put("banner", adList);
        map.put("brandList", brandList);
        map.put("hotGoodsList", hotGoodsList);
        map.put("topicList", topicList);
        map.put("floorGoodsList", floorGoodsList);

        return map;
    }
}
