package com.cskaoyan.service.wx.search;

import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.search.WxSearchIndexVO;
import com.cskaoyan.mapper.common.MarketKeywordMapper;
import com.cskaoyan.mapper.common.MarketSearchHistoryMapper;
import com.cskaoyan.util.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @since 2022/07/18 21:33
 * @author lyx
 */

@Service
public class SearchServiceImpl implements  SearchService {
    @Autowired
    MarketKeywordMapper marketKeywordMapper;
    @Autowired
    MarketSearchHistoryMapper marketSearchHistoryMapper;

    @Override
    public WxSearchIndexVO index() {
        WxSearchIndexVO wxSearchIndexVO = new WxSearchIndexVO();
        MarketKeywordExample example = new MarketKeywordExample();
        MarketKeywordExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false)
                .andIsDefaultEqualTo(true);

        MarketKeywordExample example2 = new MarketKeywordExample();
        MarketKeywordExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andIsHotEqualTo(true)
                .andDeletedEqualTo(false);

        MarketSearchHistoryExample example1 = new MarketSearchHistoryExample();
        MarketSearchHistoryExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andDeletedEqualTo(false);

        List<MarketKeyword> marketDefaultKeywords1 = marketKeywordMapper.selectByExample(example);

        List<MarketKeyword> marketHotKeywords = marketKeywordMapper.selectByExample(example2);

        List<MarketSearchHistory> marketSearchHistories = marketSearchHistoryMapper.selectByExample(example1);


        wxSearchIndexVO.setDefaultKeyword( marketDefaultKeywords1);
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
                .andKeywordLike("%"+keyword+"%");

        List<MarketKeyword> marketDefaultKeywords1 = marketKeywordMapper.selectByExample(example);
        for (MarketKeyword marketKeyword : marketDefaultKeywords1) {
            list.add(marketKeyword.getKeyword()) ;
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
            marketSearchHistoryMapper.updateByExample(marketSearchHistory, example);
        }

    }

    private Integer getMarketUserId() {
        MarketUser marketUser = PrincipalUtil.getUserInfo();
        return marketUser.getId();
    }
}
