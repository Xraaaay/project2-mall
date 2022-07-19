package com.cskaoyan.bean.wx;

import com.cskaoyan.bean.MarketKeyword;
import com.cskaoyan.bean.MarketSearchHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 
 * @since 2022/07/18 21:48
 * @author lyx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxSearchIndexVO {


    private List<MarketKeyword>  defaultKeyword;
    private List<MarketKeyword> hotKeywordList;
    private List<MarketSearchHistory> historyKeywordList;



}
