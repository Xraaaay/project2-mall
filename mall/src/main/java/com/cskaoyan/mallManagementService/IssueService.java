package com.cskaoyan.mallManagementService;

import com.cskaoyan.bean.MarketIssue;
import com.cskaoyan.bean.mallManagement.BaseParam;
import com.cskaoyan.bean.mallManagement.IssueAndKeywordListVo;


/**
 * @author: shn
 * @date:2022/7/16 14:28
 */
public interface IssueService {
    /*查询全部issue*/
    IssueAndKeywordListVo issueList(BaseParam baseParam);
    /*编辑问题*/
    MarketIssue updateIssue(MarketIssue marketIssue);
    /*删除问题*/
    void deleteIssue(MarketIssue marketIssue);
}
