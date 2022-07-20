package com.cskaoyan.service.wx.wxfeedback;

import com.cskaoyan.bean.common.MarketFeedback;

/**
 * @author: shn
 * @date:2022/7/19 17:07
 */
public interface FeedbackWXService {
    Integer submitFeedback(MarketFeedback marketFeedback);
}
