package com.cskaoyan.service.wx.wxcollect;

import com.cskaoyan.bean.admin.mallmanagement.BaseParam;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.bean.common.MarketCollect;
import com.cskaoyan.bean.common.MarketCollectExample;
import com.cskaoyan.bean.common.MarketGoods;
import com.cskaoyan.bean.common.MarketUser;
import com.cskaoyan.bean.wx.wxcollect.InnerListOfWXCollectVo;

import com.cskaoyan.mapper.common.MarketCollectMapper;
import com.cskaoyan.mapper.common.MarketGoodsMapper;
import com.cskaoyan.util.PrincipalUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 小程序 收藏模块
 *
 * @author shn
 * @date 2022/07/19 21:22
 */
@Service
public class CollectWXServiceImpl implements CollectWXService {
    @Autowired
    MarketCollectMapper marketCollectMapper;
    @Autowired
    MarketGoodsMapper marketGoodsMapper;

    /**
     * 小程序 收藏模块
     *
     * @param type
     * @param param
     * @return java.util.List<com.cskaoyan.bean.wx.wxcollect.InnerListOfWXCollectVo>
     * @author shn
     * @date 2022/7/19 22:20
     */
    @Override
    @Transactional
    public IssueAndKeywordListVo collectList(String type, BaseParam param) {
        //分页
        PageHelper.startPage(param.getPage(), param.getLimit());
        //shiro 获取用户信息
        MarketUser user = PrincipalUtil.getUserInfo();
        Integer userId = user.getId();

        //条件查询
        MarketCollectExample example = new MarketCollectExample();
        MarketCollectExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId)
                .andTypeEqualTo(Byte.valueOf(type))
                .andDeletedEqualTo(false);

        //保存赋值的list
        ArrayList<InnerListOfWXCollectVo> collectVos = new ArrayList<>();

        List<MarketCollect> collectList = marketCollectMapper.selectByExample(example);
        for (MarketCollect collect : collectList) {
            MarketGoods goods = marketGoodsMapper.selectByPrimaryKey(collect.getValueId());
            if (goods!=null) {
                InnerListOfWXCollectVo collectVo = new InnerListOfWXCollectVo();
                collectVo.setBrief(goods.getBrief());
                collectVo.setPicUrl(goods.getPicUrl());
                collectVo.setValueId(collect.getValueId());
                collectVo.setName(goods.getName());
                collectVo.setId(collect.getId());
                collectVo.setType(collect.getType());
                collectVo.setRetailPrice(goods.getRetailPrice());
                //list结果
                collectVos.add(collectVo);
            }
        }
        // 会去获得一些分页信息
        PageInfo pageInfo = new PageInfo(collectVos);
        // total是总的数据量 → 是否等于users.length?不是 → 指的是如果不分页的情况下最多会查询出来多少条记录
        IssueAndKeywordListVo issueListVo = IssueAndKeywordListVo.listVo(pageInfo.getTotal(), pageInfo.getPages(), param.getLimit(), param.getPage(), collectVos);
        return issueListVo;
    }

    /**
     * 小程序 添加、取消收藏
     *
     * @param map
     * @return int
     * @author shn
     * @date 2022/7/19 22:46
     */
    @Override
    @Transactional
    public Integer addCollect(Map map) {
        MarketCollect collect = new MarketCollect();
        //shiro 获取用户信息
        MarketUser userInfo = PrincipalUtil.getUserInfo();
        Integer userId = userInfo.getId();
        Integer valueId = (Integer) map.get("valueId");
        Integer integer = (Integer) map.get("type");
        Byte type = (byte) integer.intValue();

        //判断商品收藏是否已存在
        MarketCollectExample example = new MarketCollectExample();
        MarketCollectExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId)
                .andValueIdEqualTo(valueId)
                .andTypeEqualTo(type);

        List<MarketCollect> collects = marketCollectMapper.selectByExample(example);

        if (collects.size()!=0) {
            //已存在，逻辑删除、添加
            int updateNum=0;
            for (MarketCollect marketCollect : collects) {
                //已经收藏，逻辑删除
                if (marketCollect.getDeleted().equals(false)) {
                    marketCollect.setDeleted(true);
                    marketCollect.setUpdateTime(new Date());
                } else {
                    //收藏已经取消,逻辑添加
                    marketCollect.setDeleted(false);
                    marketCollect.setUpdateTime(new Date());
                }
                updateNum = marketCollectMapper.updateByExampleSelective(marketCollect, example);
            }
            return updateNum;
        }else {
            //不存在，添加
            collect.setUserId(userId);
            collect.setValueId(valueId);
            collect.setType(type);
            collect.setAddTime(new Date());
            collect.setUpdateTime(new Date());
            collect.setDeleted(false);
            int addNum = marketCollectMapper.insertSelective(collect);
            return addNum;
        }
    }
}
