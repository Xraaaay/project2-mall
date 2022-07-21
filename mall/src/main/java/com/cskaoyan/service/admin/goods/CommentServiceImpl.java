package com.cskaoyan.service.admin.goods;

import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.MarketComment;
import com.cskaoyan.bean.common.MarketCommentExample;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.mapper.common.MarketCommentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author pqk
 * @since 2022/07/17 23:22
 */
@Component
public class CommentServiceImpl implements CommentService {
    @Autowired
    MarketCommentMapper marketCommentMapper;

    @Override
    public CommonData list(Integer page, Integer limit, String sort, String order) {
        PageHelper.startPage(page, limit);
        MarketCommentExample example = new MarketCommentExample();
        MarketCommentExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        List<MarketComment> marketComments = marketCommentMapper.selectByExample(example);

        PageInfo<MarketComment> marketCommentsPageInfo = new PageInfo<>(marketComments);

        CommonData commonData = new CommonData();
        commonData.setTotal((int) marketCommentsPageInfo.getTotal());
        commonData.setPage(marketCommentsPageInfo.getPageNum());
        commonData.setPages(marketCommentsPageInfo.getPages());
        commonData.setLimit(marketCommentsPageInfo.getPageSize());

        commonData.setList(marketComments);

        return commonData;
    }

    /**
     * 评论列表
     *
     * @param param
     * @param userId
     * @param valueId
     * @return com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo
     * @author shn
     * @date 2022/7/20 21:09
     */
    @Override
    public IssueAndKeywordListVo list1(BaseParam param, Integer userId, Integer valueId) {
        //分页
        PageHelper.startPage(param.getPage(), param.getLimit());

        MarketCommentExample commentExample = new MarketCommentExample();
        commentExample.setOrderByClause(param.getSort() + " " + param.getOrder());
        //条件查询
        MarketCommentExample.Criteria criteria = commentExample.createCriteria();
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (valueId!=null) {
            criteria.andValueIdEqualTo(valueId);
        }
        List<MarketComment> commentList = marketCommentMapper.selectByExample(commentExample);
        PageInfo pageInfo = new PageInfo(commentList);
        IssueAndKeywordListVo listVo = IssueAndKeywordListVo.listVo(pageInfo.getTotal(), pageInfo.getPages(), param.getLimit(), param.getPage(), commentList);
        return listVo;
    }

    @Override
    public void delete(Map map) {
        Integer Id = (Integer) map.get("id");
        MarketComment marketComment = new MarketComment();
        marketComment.setDeleted(true);

        MarketCommentExample marketCommentExample = new MarketCommentExample();
        MarketCommentExample.Criteria criteria = marketCommentExample.createCriteria();
        criteria.andIdEqualTo(Id);

        marketCommentMapper.updateByExampleSelective(marketComment, marketCommentExample);
        return;
    }


}
