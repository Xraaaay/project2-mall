package com.cskaoyan.controller.wxcomment;

import com.cskaoyan.bean.MarketComment;
import com.cskaoyan.bean.mallmanagementVo.BaseParam;
import com.cskaoyan.bean.mallmanagementVo.BaseRespSuccessVo;
import com.cskaoyan.bean.mallmanagementVo.IssueAndKeywordListVo;
import com.cskaoyan.bean.wxcomment.WXCommentVo;
import com.cskaoyan.service.wx.wxcomment.CommentWXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序端的评论
 *
 * @author shn
 * @date 2022/07/18 20:57
 */
@RestController
@RequestMapping("wx/comment")
public class CommentWXController {
    @Autowired
    CommentWXService commentService;

    /**
     *  计算评论
     * @param valueId
     * @param type
     * @return com.cskaoyan.bean.mallmanagementVo.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/18 21:07
     */
    @GetMapping("count")
    public BaseRespSuccessVo count(String valueId, String type) {
        WXCommentVo commentVo=commentService.commentCount(valueId,type);
        return BaseRespSuccessVo.success(commentVo);
    }
    /**
     * 显示评论
     * @param marketComment
     * @param showType
     * @param baseParam
     * @return com.cskaoyan.bean.mallmanagementVo.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/18 22:24
     */
    @GetMapping("list")
    public BaseRespSuccessVo list(MarketComment marketComment, String showType, BaseParam baseParam) {
        IssueAndKeywordListVo commentList=commentService.commentList(marketComment,showType,baseParam);
        return BaseRespSuccessVo.success(commentList);
    }
}
