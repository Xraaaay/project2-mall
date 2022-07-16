package com.cskaoyan.mallManagementService;

import com.cskaoyan.bean.MarketKeyword;
import com.cskaoyan.bean.MarketKeywordExample;
import com.cskaoyan.bean.mallManagement.BaseParam;
import com.cskaoyan.bean.mallManagement.IssueAndKeywordListVo;
import com.cskaoyan.mapper.MarketKeywordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 关键词模块
 * @author shn
 * @date 2022/07/16 15:41
 */
@Service
public class KeywordServiceImpl implements KeywordService{
    @Autowired
    MarketKeywordMapper marketKeywordMapper;

    /**
     * 获取关键词列表
     * @param param
     * @return com.cskaoyan.bean.mallManagement.IssueAndKeywordListVo
     * @author shn
     * @date 2022/7/16 15:52
     */
    @Override
    public IssueAndKeywordListVo keywordlist(BaseParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());

        MarketKeywordExample example = new MarketKeywordExample();
        example.setOrderByClause(param.getSort() + " " + param.getOrder());

        MarketKeywordExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);

        List<MarketKeyword> marketKeywords = marketKeywordMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(marketKeywords);
        // total是总的数据量 → 是否等于users.length?不是 → 指的是如果不分页的情况下最多会查询出来多少条记录
        IssueAndKeywordListVo keywordListVo = IssueAndKeywordListVo.listVo(pageInfo.getTotal(), pageInfo.getPages(), param.getLimit(), param.getPage(), marketKeywords);
        return keywordListVo;
    }

    /**
     * 关键词 修改
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
        marketKeywordMapper.updateByPrimaryKeySelective(keyword);
    }
}
