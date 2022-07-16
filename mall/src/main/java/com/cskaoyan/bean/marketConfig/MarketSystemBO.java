package com.cskaoyan.bean.marketConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @since 2022/07/16 20:47
 * @author lyx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketSystemBO {


    /**
     * market_mall_longitude : 121.587839
     * market_mall_latitude : 31.201900
     * market_mall_address : 武汉从
     * market_mall_phone : 021-xxxx-xxxx
     * market_mall_qq : 139310186
     * market_mall_name : marketSss
     */
    private String market_mall_longitude;
    private String market_mall_latitude;
    private String market_mall_address;
    private String market_mall_phone;
    private String market_mall_qq;
    private String market_mall_name;

    public void setMarket_mall_longitude(String market_mall_longitude) {
        this.market_mall_longitude = market_mall_longitude;
    }

    public void setMarket_mall_latitude(String market_mall_latitude) {
        this.market_mall_latitude = market_mall_latitude;
    }

    public void setMarket_mall_address(String market_mall_address) {
        this.market_mall_address = market_mall_address;
    }

    public void setMarket_mall_phone(String market_mall_phone) {
        this.market_mall_phone = market_mall_phone;
    }

    public void setMarket_mall_qq(String market_mall_qq) {
        this.market_mall_qq = market_mall_qq;
    }

    public void setMarket_mall_name(String market_mall_name) {
        this.market_mall_name = market_mall_name;
    }

    public String getMarket_mall_longitude() {
        return market_mall_longitude;
    }

    public String getMarket_mall_latitude() {
        return market_mall_latitude;
    }

    public String getMarket_mall_address() {
        return market_mall_address;
    }

    public String getMarket_mall_phone() {
        return market_mall_phone;
    }

    public String getMarket_mall_qq() {
        return market_mall_qq;
    }

    public String getMarket_mall_name() {
        return market_mall_name;
    }
}
