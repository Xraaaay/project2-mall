package com.cskaoyan.service.wx.wxfeedback;

import com.cskaoyan.bean.common.MarketFeedback;
import com.cskaoyan.bean.common.MarketUser;
import com.cskaoyan.mapper.common.MarketFeedbackMapper;
import com.cskaoyan.util.GetUserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 小程序端 反馈模块
 *
 * @author shn
 * @date 2022/07/19 17:07
 */
@Service
public class FeedbackWXServiceImpl implements FeedbackWXService {
    @Autowired
    MarketFeedbackMapper marketFeedbackMapper;
    /**
     * 反馈模块
     * @param marketFeedback
     * @return java.lang.Integer
     * @author shn
     * @date 2022/7/19 17:12
     */
    @Override
    public Integer submitFeedback(MarketFeedback marketFeedback) {
       //shiro 获取用户信息
        MarketUser principal = GetUserInfoUtil.getUserInfo();

        marketFeedback.setUsername(principal.getUsername());
        marketFeedback.setUserId(principal.getId());

        marketFeedback.setAddTime(new Date());
        marketFeedback.setUpdateTime(new Date());
        int addNum = marketFeedbackMapper.insertSelective(marketFeedback);
        return addNum;
    }
}
