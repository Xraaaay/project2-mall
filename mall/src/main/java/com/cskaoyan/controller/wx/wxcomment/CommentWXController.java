package com.cskaoyan.controller.wx.wxcomment;

import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.MarketComment;
import com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.bean.wx.wxcomment.WXCommentVo;
import com.cskaoyan.service.wx.wxcomment.CommentWXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @return com.cskaoyan.bean.admin.mallmanagement.vo.BaseRespSuccessVo
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
     * @return com.cskaoyan.bean.admin.mallmanagement.vo.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/18 22:24
     */
    @GetMapping("list")
    public BaseRespSuccessVo list(MarketComment marketComment, String showType, BaseParam baseParam) {
        IssueAndKeywordListVo commentList=commentService.commentList(marketComment,showType,baseParam);
        return BaseRespSuccessVo.success(commentList);
    }

    /**
     * 提交评论
     * @param marketComment
     * @return
     */
    @PostMapping("post")
    public BaseRespSuccessVo post(@RequestBody MarketComment marketComment){
        MarketComment comment=commentService.postComment(marketComment);
        return BaseRespSuccessVo.success(comment);
    }
}
