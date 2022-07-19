package com.cskaoyan.service.wx.feedback;

import com.cskaoyan.bean.common.MarketFeedback;
import com.cskaoyan.bean.common.User;
import com.cskaoyan.mapper.common.MarketFeedbackMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
        //Subject subject = SecurityUtils.getSubject();
        //User principal = (User) subject.getPrincipals().getPrimaryPrincipal();
        //marketFeedback.setUsername(principal.getUsername());
        marketFeedback.setUsername("user123");
        //marketFeedback.setUserId(principal.getId());
        marketFeedback.setUserId(1);
        marketFeedback.setAddTime(new Date());
        marketFeedback.setUpdateTime(new Date());
        int addNum = marketFeedbackMapper.insertSelective(marketFeedback);
        return addNum;
    }
}
