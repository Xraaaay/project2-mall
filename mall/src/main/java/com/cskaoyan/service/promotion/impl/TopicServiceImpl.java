package com.cskaoyan.service.promotion.impl;

import com.cskaoyan.bean.MarketGoods;
import com.cskaoyan.bean.MarketGoodsExample;
import com.cskaoyan.bean.MarketTopic;
import com.cskaoyan.bean.MarketTopicExample;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.mapper.MarketGoodsMapper;
import com.cskaoyan.mapper.MarketTopicMapper;
import com.cskaoyan.service.promotion.TopicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import jdk.nashorn.internal.objects.NativeUint8Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 专题管理
 *
 * @author fanxing056
 * @date 2022/07/16 21:02
 */

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    MarketTopicMapper topicMapper;

    @Autowired
    MarketGoodsMapper goodsMapper;

    @Override
    public CommonData<MarketTopic> list(BasePageInfo basePageInfo, String title, String subtitle) {

        // 开启分页
        PageHelper.startPage(basePageInfo.getPage(), basePageInfo.getLimit());

        // 添加条件
        MarketTopicExample topicExample = new MarketTopicExample();
        MarketTopicExample.Criteria criteria = topicExample.createCriteria();
        topicExample.setOrderByClause(basePageInfo.getSort() + " " + basePageInfo.getOrder());
        criteria.andDeletedEqualTo(false);
        if (!StringUtil.isEmpty(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        if (!StringUtil.isEmpty(subtitle)) {
            criteria.andSubtitleLike("%" + subtitle + "%");
        }

        List<MarketTopic> marketTopicList = topicMapper.selectByExample(topicExample);
        PageInfo<MarketTopic> pageInfo = new PageInfo<>(marketTopicList);
        return CommonData.data(pageInfo);
    }

    @Transactional
    @Override
    public Map<String, Object> read(Integer id) {

        // 获取专题信息
        MarketTopic topic = topicMapper.selectByPrimaryKey(id);

        List<MarketGoods> goodsList = new ArrayList<>();
        // 查询商品表
        if (topic.getGoods().length > 0) {
            // 查询商品表获取商品信息
            MarketGoodsExample goodsExample = new MarketGoodsExample();
            // 设置条件
            MarketGoodsExample.Criteria criteria = goodsExample.createCriteria();
            criteria.andDeletedEqualTo(false)
                    .andIdIn(Arrays.asList(topic.getGoods()));
            // 获取商品列表
            goodsList = goodsMapper.selectByExample(goodsExample);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("goodsList", goodsList);
        map.put("topic", topic);
        return map;
    }

    // TODO:商品最低价需要维护？是后台管理员手动指定的
    @Transactional
    @Override
    public int create(MarketTopic topic) {

        topic.setAddTime(new Date());
        topic.setUpdateTime(new Date());

        int affect = topicMapper.insertSelective(topic);
        return affect;
    }

    // TODO:商品最低价需要维护？是后台管理员手动指定的
    @Transactional
    @Override
    public int update(MarketTopic topic) {

        topic.setUpdateTime(new Date());

        int affect = topicMapper.updateByPrimaryKeySelective(topic);

        return affect;
    }

    @Override
    public int delete(MarketTopic topic) {

        topic.setDeleted(true);
        int affect = topicMapper.updateByPrimaryKeySelective(topic);
        return affect;
    }

    @Transactional
    @Override
    public int batchDelete(Map<String, List<Integer>> map) {

        List<Integer> ids = map.get("ids");

        MarketTopicExample topicExample = new MarketTopicExample();
        MarketTopicExample.Criteria criteria = topicExample.createCriteria();

        // 添加条件
        criteria.andIdIn(ids);
        MarketTopic topic = new MarketTopic();
        topic.setDeleted(true);
        int affect = topicMapper.updateByExampleSelective(topic, topicExample);
        return affect;
    }

    @Override
    public CommonData<MarketTopic> related(Integer id) {

        BasePageInfo basePageInfo = new BasePageInfo();
        basePageInfo.setPage(1);
        basePageInfo.setLimit(5);
        basePageInfo.setSort("add_time");
        basePageInfo.setOrder("desc");
        CommonData<MarketTopic> commonData = list(basePageInfo, null, null);
        List<MarketTopic> list = commonData.getList();
        // 从list中移除自身
        int count = 0;
        for (MarketTopic topic : list) {
            if (topic.getId().equals(id)) {
                break;
            }
            count++;
        }
        list.remove(count);

        return commonData;
    }
}
