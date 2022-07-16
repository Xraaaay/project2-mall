package com.cskaoyan.mallManagementService;

import com.cskaoyan.bean.MarketIssue;
import com.cskaoyan.bean.MarketIssueExample;
import com.cskaoyan.bean.mallManagement.BaseParam;
import com.cskaoyan.bean.mallManagement.IssueAndKeywordListVo;
import com.cskaoyan.mapper.MarketIssueMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;


/**
 * 通用问题模块
 * @author shn
 * @date 2022/07/16 14:28
 */
@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    MarketIssueMapper marketIssueMapper;

    /**
     *  获取全部issue
     * @param param
     * @return com.cskaoyan.bean.mallManagement.IssueAndKeywordListVo
     * @author shn
     * @date 2022/7/16 15:35
     */
    @Override
    public IssueAndKeywordListVo issueList(BaseParam param) {
        // 在执行查询之前，可以开启分页
        // sql语句预编译的过程给你拼接上分页的sql
        PageHelper.startPage(param.getPage(), param.getLimit());

        MarketIssueExample example = new MarketIssueExample();
        example.setOrderByClause(param.getSort() + " " + param.getOrder());

        MarketIssueExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);

        List<MarketIssue> marketIssues = marketIssueMapper.selectByExample(example);
        // 会去获得一些分页信息
        PageInfo pageInfo = new PageInfo(marketIssues);
        // total是总的数据量 → 是否等于users.length?不是 → 指的是如果不分页的情况下最多会查询出来多少条记录
        IssueAndKeywordListVo issueListVo = IssueAndKeywordListVo.listVo(pageInfo.getTotal(), pageInfo.getPages(), param.getLimit(), param.getPage(), marketIssues);
        return issueListVo;
    }

    /**
     * 修改 问题
     * @param marketIssue
     * @return com.cskaoyan.bean.MarketIssue
     * @author shn
     * @date 2022/7/16 16:49
     */
    @Override
    @Transactional
    public MarketIssue updateIssue(MarketIssue marketIssue) {
        marketIssue.setUpdateTime(new Date());
        Integer id = marketIssue.getId();
        marketIssueMapper.updateByPrimaryKeySelective(marketIssue);
        MarketIssue issue = marketIssueMapper.selectByPrimaryKey(id);
        return issue;
    }
    /**
     * 通用问题 逻辑删除
     * @param marketIssue
     * @return void
     * @author shn
     * @date 2022/7/16 21:24
     */
    @Override
    @Transactional
    public void deleteIssue(MarketIssue marketIssue) {
        MarketIssue issue = new MarketIssue();
        issue.setId(marketIssue.getId());
        issue.setDeleted(true);
        marketIssueMapper.updateByPrimaryKeySelective(issue);
    }
}
