package com.cskaoyan.service.mallmanagement;

import com.cskaoyan.bean.MarketKeyword;
import com.cskaoyan.bean.mallmanagementVo.BaseParam;
import com.cskaoyan.bean.mallmanagementVo.IssueAndKeywordListVo;


/**
 * @author: shn
 * @date:2022/7/16 15:41
 */
public interface KeywordService {
    /*获取关键词列表*/
    IssueAndKeywordListVo keywordlist(BaseParam param, String keyword,String url);
    /*关键词 修改*/
    MarketKeyword updateKeyword(MarketKeyword marketKeyword);
    /*关键词 删除*/
    void deleteKeyword(MarketKeyword marketKeyword);
    /*关键词 添加*/
    MarketKeyword addKeyword(MarketKeyword marketKeyword);
}
