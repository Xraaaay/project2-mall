package com.cskaoyan.service.wx.wxcomment;

import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.MarketComment;
import com.cskaoyan.bean.common.MarketCommentExample;
import com.cskaoyan.bean.common.MarketUser;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.bean.wx.wxcomment.UserInfo;
import com.cskaoyan.bean.wx.wxcomment.WXCommentVo;
import com.cskaoyan.bean.wx.wxcomment.InnerListOfCommentVo;
import com.cskaoyan.mapper.common.MarketCommentMapper;
import com.cskaoyan.mapper.common.MarketUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        // 在执行查询之前，可以开启分页
        // sql语句预编译的过程给你拼接上分页的sql
        PageHelper.startPage(param.getPage(), param.getLimit());

        MarketCommentExample example = new MarketCommentExample();
        //example.setOrderByClause(param.getSort() + " " + param.getOrder());
        //条件查询
        MarketCommentExample.Criteria criteria = example.createCriteria();
        criteria.andValueIdEqualTo(marketComment.getValueId())
                .andTypeEqualTo(Byte.valueOf(marketComment.getType()));
        //保存data中的list
        List<InnerListOfCommentVo> commentVos = new ArrayList<>();
        //查询评论
        List<MarketComment> comments = marketCommentMapper.selectByExample(example);
        //遍历
        InnerListOfCommentVo listOfCommentVo = new InnerListOfCommentVo();
        try {
            for (MarketComment c : comments) {
                Integer userId = c.getUserId();
                MarketUser marketUser = marketUserMapper.selectByPrimaryKey(userId);
                UserInfo userInfo = new UserInfo();
                userInfo.setNickName(marketUser.getNickname());
                userInfo.setAvatarUrl(marketUser.getAvatar());
                listOfCommentVo.setUserInfo(userInfo);
                listOfCommentVo.setAddTime(marketComment.getAddTime());
                listOfCommentVo.setPicList(marketComment.getPicUrls());
                listOfCommentVo.setAdminContent(marketComment.getAdminContent());
                listOfCommentVo.setContent(marketComment.getContent());
                commentVos.add(listOfCommentVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 会去获得一些分页信息
        PageInfo pageInfo = new PageInfo(commentVos);
        // total是总的数据量 → 是否等于users.length?不是 → 指的是如果不分页的情况下最多会查询出来多少条记录
        IssueAndKeywordListVo issueListVo = IssueAndKeywordListVo.listVo(pageInfo.getTotal(), pageInfo.getPages(), param.getLimit(), param.getPage(), commentVos);
        return issueListVo;
    }
}
