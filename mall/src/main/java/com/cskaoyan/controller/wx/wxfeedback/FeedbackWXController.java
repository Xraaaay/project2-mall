package com.cskaoyan.controller.wx.wxfeedback;

import com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo;
import com.cskaoyan.bean.common.MarketFeedback;
import com.cskaoyan.service.wx.wxfeedback.FeedbackWXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序反馈
 *
 * @author shn
 * @date 2022/07/19 17:03
 */
@RestController
@RequestMapping("wx/feedback")
public class FeedbackWXController {
    @Autowired
    FeedbackWXService feedbackService;
    @PostMapping("submit")
    public BaseRespSuccessVo submit(@RequestBody MarketFeedback marketFeedback) {
        Integer addNum=feedbackService.submitFeedback(marketFeedback);
        if (addNum!=1) {
            return BaseRespSuccessVo.failed(null);
        }
        return BaseRespSuccessVo.success(null);
    }
}
