package com.cskaoyan.service.wx.wxcomment;

import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.bean.wx.wxcomment.*;
import com.cskaoyan.mapper.common.MarketCommentMapper;
import com.cskaoyan.mapper.common.MarketUserMapper;
import com.cskaoyan.util.PrincipalUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 小程序端的评论模块
 *
 * @author shn
 * @date 2022/07/18 21:04
 */
@Service
public class CommentWXServiceImpl implements CommentWXService {
    @Autowired
    MarketCommentMapper marketCommentMapper;
    @Autowired
    MarketUserMapper marketUserMapper;

    /**
     * 评论列表
     *
     * @param valueId
     * @param type
     * @return com.cskaoyan.bean.wx.wxcomment.WXCommentVo
     * @author shn
     * @date 2022/7/18 21:15
     */
    @Override
    @Transactional
    public WXCommentVo commentCount(String valueId, String type) {
        MarketCommentExample example = new MarketCommentExample();
        MarketCommentExample.Criteria criteria = example.createCriteria();
        //条件查询
        criteria.andValueIdEqualTo(Integer.valueOf(valueId))
                .andTypeEqualTo(Byte.valueOf(type));
        long allCount = marketCommentMapper.countByExample(example);
        criteria.andValueIdEqualTo(Integer.valueOf(valueId))
                .andTypeEqualTo(Byte.valueOf(type))
                .andHasPictureEqualTo(true);
        long hasPicCount = marketCommentMapper.countByExample(example);
        WXCommentVo commentVo = new WXCommentVo();
        commentVo.setHasPicCount(hasPicCount);
        commentVo.setAllCount(allCount);
        return commentVo;
    }

    /**
     * 评论列表
     *
     * @param marketComment
     * @param showType
     * @param param
     * @return com.cskaoyan.bean.admin.mallmanagement.vo.IssueAndKeywordListVo
     * @author shn
     * @date 2022/7/18 22:27
     */
    @Override
    @Transactional
    public IssueAndKeywordListVo commentList(MarketComment marketComment, String showType, BaseParam param) {

        PageHelper.startPage(param.getPage(), param.getLimit());

        MarketCommentExample example = new MarketCommentExample();

        //条件查询
        MarketCommentExample.Criteria criteria = example.createCriteria();
        criteria.andValueIdEqualTo(marketComment.getValueId())
                .andTypeEqualTo(Byte.valueOf(marketComment.getType()));
        //保存data中的list
        List<InnerListOfCommentVo> commentVos = new ArrayList<>();
        //查询评论
        List<MarketComment> comments = marketCommentMapper.selectByExample(example);
        //遍历
        for (MarketComment c : comments) {
            Integer userId = c.getUserId();
            MarketUser marketUser = marketUserMapper.selectByPrimaryKey(userId);
            //商品评论
            UserInfo userInfo = new UserInfo();
            //专题评论
            UserInfoNickname userInfoNickName = new UserInfoNickname();
            if (marketUser!=null) {
                InnerListOfCommentVo listOfCommentVo = new InnerListOfCommentVo();
                if (Integer.valueOf(marketComment.getType())==1) {
                    //商品评论
                    userInfo.setNickName(marketUser.getNickname());
                    userInfo.setAvatarUrl(marketUser.getAvatar());
                    listOfCommentVo.setUserInfo(userInfo);
                }else {
                    //专题评论
                    userInfoNickName.setNickname(marketUser.getNickname());
                    userInfoNickName.setAvatarUrl(marketUser.getAvatar());
                    listOfCommentVo.setUserInfo(userInfoNickName);
                }
                listOfCommentVo.setAddTime(c.getAddTime());
                listOfCommentVo.setPicList(c.getPicUrls());
                listOfCommentVo.setAdminContent(c.getAdminContent());
                listOfCommentVo.setContent(c.getContent());
                commentVos.add(listOfCommentVo);
            }

        }
        // 会去获得一些分页信息
        PageInfo pageInfo = new PageInfo(commentVos);
        // total是总的数据量 → 是否等于users.length?不是 → 指的是如果不分页的情况下最多会查询出来多少条记录
        IssueAndKeywordListVo issueListVo = IssueAndKeywordListVo.listVo(pageInfo.getTotal(), pageInfo.getPages(), param.getLimit(), param.getPage(), commentVos);
        return issueListVo;
    }
    /**
     * 提交评论
     * @param marketComment
     * @return com.cskaoyan.bean.common.MarketComment
     * @author shn
     * @date 2022/7/19 16:13
     */
    @Override
    @Transactional
    public MarketComment postComment(MarketComment marketComment) {
        marketComment.setAddTime(new Date());
        marketComment.setUpdateTime(new Date());

        //shiro 获取用户信息
        MarketUser user = PrincipalUtil.getUserInfo();
        marketComment.setUserId(user.getId());

        marketCommentMapper.insertSelective(marketComment);
        Integer id = marketComment.getId();
        MarketComment comment = marketCommentMapper.selectByPrimaryKey(id);
        return comment;
    }

}
