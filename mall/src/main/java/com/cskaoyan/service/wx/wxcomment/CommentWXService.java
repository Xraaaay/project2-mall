package com.cskaoyan.service.wx.wxcomment;

import com.cskaoyan.bean.MarketComment;
import com.cskaoyan.bean.mallmanagementVo.BaseParam;
import com.cskaoyan.bean.mallmanagementVo.IssueAndKeywordListVo;
import com.cskaoyan.bean.wxcomment.WXCommentVo;

/**
 * @author: shn
 * @date:2022/7/18 21:04
 */
public interface CommentWXService {
    /*小程序端的评论计算*/
    WXCommentVo commentCount(String valueId, String type);
    /*小程序评论列表*/
    IssueAndKeywordListVo commentList(MarketComment marketComment, String showType, BaseParam baseParam);
}
