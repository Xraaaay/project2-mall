package com.cskaoyan.service.wx.search;

import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.search.WxSearchIndexVO;
import com.cskaoyan.exception.UnAuthException;
import com.cskaoyan.mapper.common.MarketKeywordMapper;
import com.cskaoyan.mapper.common.MarketSearchHistoryMapper;
import com.cskaoyan.util.PrincipalUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyx
 * @since 2022/07/18 21:33
 */

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    MarketKeywordMapper marketKeywordMapper;
    @Autowired
    MarketSearchHistoryMapper marketSearchHistoryMapper;

    @Override
    public WxSearchIndexVO index() {
        WxSearchIndexVO wxSearchIndexVO = new WxSearchIndexVO();
        MarketKeywordExample exampleDefault = new MarketKeywordExample();
        MarketKeywordExample.Criteria criteria = exampleDefault.createCriteria();
        criteria.andDeletedEqualTo(false)
                .andIsDefaultEqualTo(true);

        MarketKeywordExample exampleKeyword = new MarketKeywordExample();
        MarketKeywordExample.Criteria criteriaKeyword = exampleKeyword.createCriteria();
        criteriaKeyword.andIsHotEqualTo(true)
                .andDeletedEqualTo(false);

        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        List<MarketSearchHistory> marketSearchHistories = null;
        // 登录状态才根据id查找搜索历史
        if (principals != null) {
            MarketUser user = (MarketUser) principals.getPrimaryPrincipal();
            MarketSearchHistoryExample exampleHistory = new MarketSearchHistoryExample();
            MarketSearchHistoryExample.Criteria criteria1 = exampleHistory.createCriteria();
            criteria1.andDeletedEqualTo(false)
                    .andUserIdEqualTo(user.getId());
            marketSearchHistories = marketSearchHistoryMapper.selectByExample(exampleHistory);
        }

        List<MarketKeyword> marketDefaultKeywords1 = marketKeywordMapper.selectByExample(exampleDefault);

        List<MarketKeyword> marketHotKeywords = marketKeywordMapper.selectByExample(exampleKeyword);

        wxSearchIndexVO.setDefaultKeyword(marketDefaultKeywords1.get(0));
        wxSearchIndexVO.setHotKeywordList(marketHotKeywords);
        wxSearchIndexVO.setHistoryKeywordList(marketSearchHistories);

        return wxSearchIndexVO;
    }

    @Override
    public List<String> helper(String keyword) {
        List<String> list = new ArrayList<>();
        MarketKeywordExample example = new MarketKeywordExample();
        MarketKeywordExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false)
                .andKeywordLike("%" + keyword + "%");

        List<MarketKeyword> marketDefaultKeywords1 = marketKeywordMapper.selectByExample(example);
        for (MarketKeyword marketKeyword : marketDefaultKeywords1) {
            list.add(marketKeyword.getKeyword());
        }

        return list;
    }

    @Override
    public void clearhistory() {
        Integer userId = getMarketUserId();
        MarketSearchHistoryExample example = new MarketSearchHistoryExample();
        MarketSearchHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false)
                .andUserIdEqualTo(userId);
        List<MarketSearchHistory> marketSearchHistories = marketSearchHistoryMapper.selectByExample(example);
        for (MarketSearchHistory marketSearchHistory : marketSearchHistories) {
            marketSearchHistory.setDeleted(true);
            marketSearchHistoryMapper.updateByPrimaryKey(marketSearchHistory);
        }

    }

    private Integer getMarketUserId() {
        MarketUser marketUser = PrincipalUtil.getUserInfo();
        return marketUser.getId();
    }
}
