package com.cskaoyan.service.admin.usermanagement;

import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.*;
import com.cskaoyan.exception.InvalidParamException;
import com.cskaoyan.mapper.common.*;
import com.cskaoyan.util.Md5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户管理模块
 * (以下的或者也可以是联合)
 *
 * @author Zah
 * @date 2022/07/18 17:25
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    MarketUserMapper marketUserMapper;
    @Autowired
    MarketAddressMapper marketAddressMapper;
    @Autowired
    MarketCollectMapper marketCollectMapper;
    @Autowired
    MarketFootprintMapper marketFootprintMapper;
    @Autowired
    MarketSearchHistoryMapper marketSearchHistoryMapper;
    @Autowired
    MarketFeedbackMapper marketFeedbackMapper;

    /**
     * 按username模糊查询或者按mobile准确查询user表中的信息
     *
     * @param page
     * @param username
     * @param mobile
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/18 17:25
     */
    @Override
    public UserListVo getUserList(BaseParam page, String username, String mobile) {

        // 分页
        PageHelper.startPage(page.getPage(), page.getLimit());

        MarketUserExample userExample = new MarketUserExample();
        // 拼接SQl语句：按条件排序
        userExample.setOrderByClause(page.getSort() + " " + page.getOrder());

        MarketUserExample.Criteria criteria = userExample.createCriteria();

        // 把没有被标记删除的用户信息显示
        criteria.andDeletedEqualTo(false);
        // 根据username进行模糊查询
        if (username != null && !"".equals(username)) {

            criteria.andUsernameLike("%" + username + "%");
        }
        // 根据mobile进行准确查询
        if (mobile != null && !"".equals(mobile)) {

            criteria.andMobileEqualTo(mobile);
        }

        List<MarketUser> marketUsers = marketUserMapper.selectByExample(userExample);

        /*优化用户名密码：密文回显到页面中*/
        for (MarketUser marketUser : marketUsers) {
            marketUser.setPassword("********");
        }

        // 将查询结果作为有参构造方法的形参传入，可以获得PageInfo
        PageInfo<MarketUser> marketUserPageInfo = new PageInfo<>(marketUsers);

        // 封装整理好的数据到对象中
        UserListVo<MarketUser> marketUserListVo = new UserListVo<>(marketUserPageInfo.getTotal(), marketUserPageInfo.getPages(),
                page.getLimit(), page.getPage(), marketUsers);

        return marketUserListVo;
    }

    /**
     * 根据用户Id查询用户信息
     *
     * @param id
     * @return com.cskaoyan.bean.common.MarketUser
     * @author Zah
     * @date 2022/07/18 17:35
     */
    @Override
    public MarketUser getUserDetail(Integer id) {

        MarketUser marketUser = marketUserMapper.selectByPrimaryKey(id);

        return marketUser;
    }

    /**
     * 根据name模糊查询或者根据userId准确查询用户的收货地址详情
     *
     * @param page
     * @param name
     * @param userId
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/18 18:00
     */
    @Override
    public UserListVo getAddressList(BaseParam page, String name, Integer userId) {

        PageHelper.startPage(page.getPage(), page.getLimit());

        MarketAddressExample addressExample = new MarketAddressExample();
        addressExample.setOrderByClause(page.getSort() + " " + page.getOrder());

        MarketAddressExample.Criteria criteria = addressExample.createCriteria();

        criteria.andDeletedEqualTo(false);

        if (name != null && !"".equals(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (userId != null && !"".equals(userId)) {
            criteria.andUserIdEqualTo(userId);
        }

        List<MarketAddress> marketAddresses = marketAddressMapper.selectByExample(addressExample);

        PageInfo<MarketAddress> marketAddressPageInfo = new PageInfo<>(marketAddresses);

        UserListVo<MarketAddress> marketAddressUserListVo = new UserListVo<>(marketAddressPageInfo.getTotal(), marketAddressPageInfo.getPages(),
                page.getLimit(), page.getPage(), marketAddresses);

        return marketAddressUserListVo;

    }

    /**
     * 根据userId准确查询或者valueId准确查询会员收藏信息
     *
     * @param page
     * @param userId
     * @param valueId
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/18 19:53
     */
    @Override
    public UserListVo getCollectList(BaseParam page, Integer userId, Integer valueId) {

        PageHelper.startPage(page.getPage(), page.getLimit());

        MarketCollectExample collectExample = new MarketCollectExample();

        collectExample.setOrderByClause(page.getSort() + " " + page.getOrder());

        MarketCollectExample.Criteria criteria = collectExample.createCriteria();

        criteria.andDeletedEqualTo(false);
        if (userId != null && !"".equals(userId)) {
            criteria.andUserIdEqualTo(userId);
        }
        if (valueId != null && !"".equals(valueId)) {
            criteria.andValueIdEqualTo(valueId);
        }

        List<MarketCollect> marketCollects = marketCollectMapper.selectByExample(collectExample);

        PageInfo<MarketCollect> marketCollectPageInfo = new PageInfo<>(marketCollects);

        UserListVo<MarketCollect> marketCollectUserListVo = new UserListVo<>(marketCollectPageInfo.getTotal(), marketCollectPageInfo.getPages(),
                page.getLimit(), page.getPage(), marketCollects);

        return marketCollectUserListVo;
    }

    /**
     * 根据userId准确查询或者goodsId做准确查询会员足迹信息
     *
     * @param page
     * @param userId
     * @param goodsId
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/18 20:19
     */
    @Override
    public UserListVo getFootprintList(BaseParam page, Integer userId, Integer goodsId) {

        PageHelper.startPage(page.getPage(), page.getLimit());

        MarketFootprintExample footprintExample = new MarketFootprintExample();

        footprintExample.setOrderByClause(page.getSort() + " " + page.getOrder());

        MarketFootprintExample.Criteria criteria = footprintExample.createCriteria();

        criteria.andDeletedEqualTo(false);
        if (userId != null && !"".equals(userId)) {
            criteria.andUserIdEqualTo(userId);
        }
        if (goodsId != null && !"".equals(goodsId)) {
            criteria.andGoodsIdEqualTo(goodsId);
        }

        List<MarketFootprint> marketFootprints = marketFootprintMapper.selectByExample(footprintExample);

        PageInfo<MarketFootprint> marketFootprintPageInfo = new PageInfo<>(marketFootprints);

        UserListVo<MarketFootprint> marketFootprintUserListVo = new UserListVo<>(marketFootprintPageInfo.getTotal(), marketFootprintPageInfo.getPages(),
                page.getLimit(), page.getPage(), marketFootprints);

        return marketFootprintUserListVo;
    }

    /**
     * 根据userId做准确查询或者根据keyword做模糊查询搜索历史信息
     *
     * @param page
     * @param userId
     * @param keyword
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/18 20:38
     */
    @Override
    public UserListVo getSearchHistoryList(BaseParam page, Integer userId, String keyword) {

        PageHelper.startPage(page.getPage(), page.getLimit());

        MarketSearchHistoryExample searchHistoryExample = new MarketSearchHistoryExample();

        searchHistoryExample.setOrderByClause(page.getSort() + " " + page.getOrder());

        MarketSearchHistoryExample.Criteria criteria = searchHistoryExample.createCriteria();

        criteria.andDeletedEqualTo(false);
        if (userId != null && !"".equals(userId)) {
            criteria.andUserIdEqualTo(userId);
        }
        if (keyword != null && !"".equals(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }

        List<MarketSearchHistory> marketSearchHistories = marketSearchHistoryMapper.selectByExample(searchHistoryExample);

        PageInfo<MarketSearchHistory> marketSearchHistoryPageInfo = new PageInfo<>(marketSearchHistories);

        UserListVo<MarketSearchHistory> marketSearchHistoryUserListVo = new UserListVo<>(marketSearchHistoryPageInfo.getTotal(), marketSearchHistoryPageInfo.getPages(),
                page.getLimit(), page.getPage(), marketSearchHistories);

        return marketSearchHistoryUserListVo;
    }

    /**
     * 根据username做模糊查询或者根据id做准确查询意见反馈信息
     *
     * @param page
     * @param username
     * @param id
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/18 21:17
     */
    @Override
    public UserListVo getFeedbackList(BaseParam page, String username, Integer id) {

        PageHelper.startPage(page.getPage(),page.getLimit());

        MarketFeedbackExample feedbackExample = new MarketFeedbackExample();

        feedbackExample.setOrderByClause(page.getSort() + " " + page.getOrder());

        MarketFeedbackExample.Criteria criteria = feedbackExample.createCriteria();

        criteria.andDeletedEqualTo(false);
        if (username!=null&&!"".equals(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        if (id!=null&!"".equals(id)){
            criteria.andIdEqualTo(id);
        }

        List<MarketFeedback> marketFeedbacks = marketFeedbackMapper.selectByExample(feedbackExample);

        PageInfo<MarketFeedback> marketFeedbackPageInfo = new PageInfo<>(marketFeedbacks);

        UserListVo<MarketFeedback> marketFeedbackUserListVo = new UserListVo<>(marketFeedbackPageInfo.getTotal(), marketFeedbackPageInfo.getPages(),
                page.getLimit(), page.getPage(), marketFeedbacks);

        return marketFeedbackUserListVo;
    }

    /**
     * 用户信息更新
     * @param marketUser
     * @return java.lang.Integer
     * @author shn
     * @date 2022/7/20 11:39
     */
    @Override
    @Transactional
    public Integer updateUser(MarketUser marketUser) {
        String newPassword=null;
        if (marketUser.getPassword()!=null) {
            try {
                //Md5加密
                newPassword = Md5Utils.getMd5(marketUser.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MarketUserExample userExample = new MarketUserExample();
        MarketUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(marketUser.getId());
        marketUser.setUpdateTime(new Date());
        marketUser.setPassword(newPassword);
        int updateNum = marketUserMapper.updateByExampleSelective(marketUser, userExample);
        return updateNum;
    }
}


