package com.cskaoyan.service.mallmanagement;

import com.cskaoyan.bean.MarketIssue;
import com.cskaoyan.bean.MarketIssueExample;
import com.cskaoyan.bean.mallmanagementVo.BaseParam;
import com.cskaoyan.bean.mallmanagementVo.IssueAndKeywordListVo;
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
     *  获取issue列表
     * @param param
     * @return com.cskaoyan.bean.mallmanagementVo.IssueAndKeywordListVo
     * @author shn
     * @date 2022/7/16 15:35
     */
    @Override
    public IssueAndKeywordListVo issueList(BaseParam param,String question) {
        // 在执行查询之前，可以开启分页
        // sql语句预编译的过程给你拼接上分页的sql
        PageHelper.startPage(param.getPage(), param.getLimit());

        MarketIssueExample example = new MarketIssueExample();
        example.setOrderByClause(param.getSort() + " " + param.getOrder());
        //添加查询条件：未被删除的
        MarketIssueExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        //按照 “问题” 查找：
        if (question!=null) {
            criteria.andQuestionLike("%"+question+"%");
        }
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
        issue.setUpdateTime(new Date());
        marketIssueMapper.updateByPrimaryKeySelective(issue);
    }

    /**
     * 添加问题
     * @param marketIssue
     * @return com.cskaoyan.bean.MarketIssue
     * @author shn
     * @date 2022/7/17 16:04
     */
    @Override
    public MarketIssue addIssue(MarketIssue marketIssue) {
        MarketIssue issue = new MarketIssue();
        issue.setQuestion(marketIssue.getQuestion());
        issue.setAnswer(marketIssue.getAnswer());
        issue.setAddTime(new Date());
        marketIssueMapper.insertSelective(issue);
        MarketIssue addIssue = marketIssueMapper.selectByPrimaryKey(issue.getId());
        return addIssue;
    }
}
