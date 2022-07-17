package com.cskaoyan.bean.marketConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @since 2022/07/17 21:23
 * @author lyx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketWxBO {
    private String  market_wx_index_new;
    private String market_wx_index_topic;
    private String market_wx_index_brand;
    private String market_wx_share;
    private String market_wx_catlog_list;
    private String market_wx_catlog_goods;
    private String market_wx_index_hot;
}
