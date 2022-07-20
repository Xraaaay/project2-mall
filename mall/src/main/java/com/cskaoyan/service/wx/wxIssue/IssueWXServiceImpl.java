package com.cskaoyan.service.wx.wxIssue;

import com.cskaoyan.bean.admin.mallmanagement.BaseParam;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.bean.common.MarketIssue;
import com.cskaoyan.bean.common.MarketIssueExample;
import com.cskaoyan.mapper.common.MarketIssueMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 小程序 帮助中心模块
 *
 * @author shn
 * @date 2022/07/20 10:04
 */
@Service
public class IssueWXServiceImpl implements IssueWXService{
    @Autowired
    MarketIssueMapper marketIssueMapper;

    /**
     * 小程序 帮助中心
     *
     * @param param
     * @return com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo
     * @author shn
     * @date 2022/7/20 10:08
     */
    @Override
    public IssueAndKeywordListVo issueList(BaseParam param) {
        //分页
        PageHelper.startPage(param.getPage(),param.getLimit());

        MarketIssueExample example = new MarketIssueExample();
        List<MarketIssue> issueList = marketIssueMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(issueList);
        IssueAndKeywordListVo listVo = IssueAndKeywordListVo.listVo(pageInfo.getTotal(), pageInfo.getPages(), param.getLimit(), param.getPage(), issueList);
        return listVo;
    }
}
