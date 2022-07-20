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
import com.cskaoyan.util.GetUserInfoUtil;
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
        MarketUser user = GetUserInfoUtil.getUserInfo();
        Integer userId = user.getId();

        //条件查询
        MarketCollectExample example = new MarketCollectExample();
        MarketCollectExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId)
                .andTypeEqualTo(Byte.valueOf(type));
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
     * 小程序 添加收藏
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
        MarketUser userInfo = GetUserInfoUtil.getUserInfo();

        collect.setUserId(userInfo.getId());
        collect.setValueId((Integer) map.get("valueId"));
        Integer integer = (Integer) map.get("type");
        Byte type = (byte) integer.intValue();
        collect.setType(type);
        collect.setAddTime(new Date());
        collect.setUpdateTime(new Date());
        collect.setDeleted(false);
        int addNum = marketCollectMapper.insertSelective(collect);
        return addNum;
    }
}
