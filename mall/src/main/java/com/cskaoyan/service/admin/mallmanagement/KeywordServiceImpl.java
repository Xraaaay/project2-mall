package com.cskaoyan.service.admin.mallmanagement;

import com.cskaoyan.bean.common.MarketKeyword;
import com.cskaoyan.bean.common.MarketKeywordExample;
import com.cskaoyan.bean.admin.mallmanagement.BaseParam;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.mapper.common.MarketKeywordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 关键词模块
 *
 * @author shn
 * @date 2022/07/16 15:41
 */
@Service
public class KeywordServiceImpl implements KeywordService {
    @Autowired
    MarketKeywordMapper marketKeywordMapper;

    /**
     * 获取关键词列表
     *
     * @param param
     * @return com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo
     * @author shn
     * @date 2022/7/16 15:52
     */
    @Override
    public IssueAndKeywordListVo keywordlist(BaseParam param, String keyword, String url) {
        PageHelper.startPage(param.getPage(), param.getLimit());

        MarketKeywordExample example = new MarketKeywordExample();
        example.setOrderByClause(param.getSort() + " " + param.getOrder());
        //添加查询条件：未被删除的
        MarketKeywordExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        //按照 “关键词”、“url” 查询
        if (keyword != null && url != null) {
            criteria.andKeywordLike("%" + keyword + "%")
                    .andUrlLike("%" + url + "%");
        } else if (keyword != null) {
            criteria.andKeywordLike("%" + keyword + "%");
        } else if (url != null) {
            criteria.andUrlLike("%" + url + "%");
        }
        List<MarketKeyword> marketKeywords = marketKeywordMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(marketKeywords);
        // total是总的数据量 → 是否等于users.length?不是 → 指的是如果不分页的情况下最多会查询出来多少条记录
        IssueAndKeywordListVo keywordListVo = IssueAndKeywordListVo.listVo(pageInfo.getTotal(), pageInfo.getPages(), param.getLimit(), param.getPage(), marketKeywords);
        return keywordListVo;
    }

    /**
     * 关键词 修改
     *
     * @param marketKeyword
     * @return com.cskaoyan.bean.MarketKeywordVo
     * @author shn
     * @date 2022/7/16 20:02
     */
    @Override
    public MarketKeyword updateKeyword(MarketKeyword marketKeyword) {
        marketKeyword.setUpdateTime(new Date());
        marketKeywordMapper.updateByPrimaryKeySelective(marketKeyword);
        Integer id = marketKeyword.getId();
        MarketKeyword keyword = marketKeywordMapper.selectByPrimaryKey(id);
        return keyword;
    }

    /**
     * 逻辑删除关键词
     *
     * @param marketKeyword
     * @return void
     * @author shn
     * @date 2022/7/16 21:32
     */
    @Override
    public void deleteKeyword(MarketKeyword marketKeyword) {
        MarketKeyword keyword = new MarketKeyword();
        keyword.setId(marketKeyword.getId());
        keyword.setDeleted(true);
        keyword.setUpdateTime(new Date());
        marketKeywordMapper.updateByPrimaryKeySelective(keyword);
    }

    /**
     * 关键词添加
     *
     * @param marketKeyword
     * @return com.cskaoyan.bean.common.MarketKeyword
     * @author shn
     * @date 2022/7/17 16:51
     */
    @Override
    public MarketKeyword addKeyword(MarketKeyword marketKeyword) {
        MarketKeyword addKeyword = new MarketKeyword();
        addKeyword.setKeyword(marketKeyword.getKeyword());
        addKeyword.setUrl(marketKeyword.getUrl());
        addKeyword.setIsHot(marketKeyword.getIsHot());
        addKeyword.setIsDefault(marketKeyword.getIsDefault());
        addKeyword.setAddTime(new Date());
        marketKeywordMapper.insertSelective(addKeyword);
        MarketKeyword keyword = marketKeywordMapper.selectByPrimaryKey(addKeyword.getId());
        return keyword;
    }
}
