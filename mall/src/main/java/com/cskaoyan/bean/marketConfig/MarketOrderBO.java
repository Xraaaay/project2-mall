package com.cskaoyan.bean.marketConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @since 2022/07/17 21:08
 * @author lyx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketOrderBO {


    /**
     * market_order_unconfirm : 7
     * market_order_comment : 7
     * market_order_unpaid : 30
     */
    private String market_order_unconfirm;
    private String market_order_comment;
    private String market_order_unpaid;

    public void setMarket_order_unconfirm(String market_order_unconfirm) {
        this.market_order_unconfirm = market_order_unconfirm;
    }

    public void setMarket_order_comment(String market_order_comment) {
        this.market_order_comment = market_order_comment;
    }

    public void setMarket_order_unpaid(String market_order_unpaid) {
        this.market_order_unpaid = market_order_unpaid;
    }

    public String getMarket_order_unconfirm() {
        return market_order_unconfirm;
    }

    public String getMarket_order_comment() {
        return market_order_comment;
    }

    public String getMarket_order_unpaid() {
        return market_order_unpaid;
    }
}
