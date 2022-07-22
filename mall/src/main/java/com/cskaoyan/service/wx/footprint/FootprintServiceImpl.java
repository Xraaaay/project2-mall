package com.cskaoyan.service.wx.footprint;

import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.footprint.FootprintInfoVo;
import com.cskaoyan.mapper.common.MarketFootprintMapper;
import com.cskaoyan.mapper.common.MarketGoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 浏览足迹
 *
 * @author Zah
 * @date 2022/07/20 09:16
 */
@Service
public class FootprintServiceImpl implements FootprintService {

    @Autowired
    MarketGoodsMapper marketGoodsMapper;

    @Autowired
    MarketFootprintMapper marketFootprintMapper;

    /**
     * 用户浏览足迹列表
     * @param page
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/20 10:38
     */
    @Override
    public UserListVo getFootprintList(BaseParam page) {

        PageHelper.startPage(page.getPage(),page.getLimit());

        Subject subject = SecurityUtils.getSubject();

        if (subject.getPrincipals() == null ){

            subject.logout();
            return null;
        }

        // 先拿到用户的id
        MarketUser primaryPrincipal = (MarketUser) subject.getPrincipals().getPrimaryPrincipal();

        // 把封装好的footprint的信息放到集合中
        List<FootprintInfoVo> footprintList = new ArrayList<>();


        // 根据用户的id到footprint中拿到id,goodsid,addTime
        MarketFootprintExample footprintExample = new MarketFootprintExample();
        footprintExample.setOrderByClause("update_time desc");

        // 只显示delete字段为false的信息
        footprintExample.createCriteria().andDeletedEqualTo(false);

        footprintExample.createCriteria().andUserIdEqualTo(primaryPrincipal.getId());

        List<MarketFootprint> marketFootprints = marketFootprintMapper.selectByExample(footprintExample);

        // 再根据footprint中的goodid拿到goods表中的brief,picUrl,name,retailPrice
        for (MarketFootprint marketFootprint : marketFootprints) {

            MarketGoodsExample goodsExample = new MarketGoodsExample();

            goodsExample.createCriteria().andIdEqualTo(marketFootprint.getGoodsId());

            List<MarketGoods> marketGoods = marketGoodsMapper.selectByExample(goodsExample);

            for (MarketGoods marketGood : marketGoods) {

                // 封装footprint表中散乱的信息
                FootprintInfoVo footprintInfoVo = new FootprintInfoVo();

                footprintInfoVo.setBrief(marketGood.getBrief());
                footprintInfoVo.setPicUrl(marketGood.getPicUrl());
                footprintInfoVo.setAddTime(marketFootprint.getAddTime());
                footprintInfoVo.setGoodsId(marketFootprint.getGoodsId());
                footprintInfoVo.setName(marketGood.getName());
                footprintInfoVo.setId(marketFootprint.getId());
                footprintInfoVo.setRetailPrice(marketGood.getRetailPrice());

                footprintList.add(footprintInfoVo);
            }
        }

        PageInfo<MarketFootprint> marketFootprintPageInfo = new PageInfo<>(marketFootprints);

        UserListVo<FootprintInfoVo> marketFootprintUserListVo = new UserListVo<>(marketFootprintPageInfo.getTotal(), marketFootprintPageInfo.getPages(),
                page.getPage(), page.getLimit(), footprintList);

        return marketFootprintUserListVo;
    }

    /**
     * 逻辑删除用户浏览足迹
     * @param id
     * @return void
     * @author Zah
     * @date 2022/07/20 10:39
     */
    @Override
    public void postFootprintDelete(Integer id) {

        marketFootprintMapper.updateByPrimaryKeySelective(new MarketFootprint(id,true));

    }
}
