package com.cskaoyan.service.mallmanagement;

import com.cskaoyan.bean.MarketIssue;
import com.cskaoyan.bean.mallmanagementVo.BaseParam;
import com.cskaoyan.bean.mallmanagementVo.IssueAndKeywordListVo;


/**
 * @author: shn
 * @date:2022/7/16 14:28
 */
public interface IssueService {
    /*查询全部issue*/
    IssueAndKeywordListVo issueList(BaseParam baseParam,String question);
    /*编辑问题*/
    MarketIssue updateIssue(MarketIssue marketIssue);
    /*删除问题*/
    void deleteIssue(MarketIssue marketIssue);
    /*添加问题*/
    MarketIssue addIssue(MarketIssue marketIssue);
}
