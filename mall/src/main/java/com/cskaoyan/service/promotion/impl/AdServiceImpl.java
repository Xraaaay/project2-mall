package com.cskaoyan.service.promotion.impl;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.MarketAd;
import com.cskaoyan.bean.MarketAdExample;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.mapper.MarketAdMapper;
import com.cskaoyan.service.promotion.AdService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 广告
 * @date 2022/07/16 14:12
 * @author fanxing056
 */

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    MarketAdMapper adMapper;

    @Override
    public CommonData<MarketAd> query(Integer page, Integer limit, String sort, String order, String name, String content) {

        // 开启分页
        PageHelper.startPage(page, limit, sort + " " + order);

        MarketAdExample marketAdExample = new MarketAdExample();
        MarketAdExample.Criteria criteria = marketAdExample.createCriteria();

        // 添加条件
        criteria.andDeletedEqualTo(false);
        if (!StringUtil.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtil.isEmpty(content)) {
            criteria.andContentLike("%" + content + "%");
        }

        List<MarketAd> marketAds = adMapper.selectByExample(marketAdExample);

        PageInfo<MarketAd> pageInfo = new PageInfo<>(marketAds);

        return CommonData.data(pageInfo);
    }


    @Transactional
    @Override
    public int create(MarketAd ad) {

        int affect = adMapper.insertSelective(ad);

        return affect;
    }

    @Transactional
    @Override
    public int delete(MarketAd ad) {

        ad.setDeleted(true);
        int affect = adMapper.updateByPrimaryKeySelective(ad);
        return affect;
    }

    // TODO:日期格式
    @Transactional
    @Override
    public int update(MarketAd ad) {

        ad.setUpdateTime(new Date());

        int affect = adMapper.updateByPrimaryKeySelective(ad);

        return affect;
    }
}
