package com.cskaoyan.service.wx.wxIssue;

import com.cskaoyan.bean.admin.mallmanagement.BaseParam;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;

/**
 * @author: shn
 * @date:2022/7/20 10:03
 */
public interface IssueWXService {
    /*小程序 帮助中心*/
    IssueAndKeywordListVo issueList(BaseParam param);
}
