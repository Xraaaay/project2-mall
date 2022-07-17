package com.cskaoyan.bean.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author changyong
 * @since 2022/07/17 18:47
 */
@Data
public class MarketBrandCreateBo {
    String desc;
    BigDecimal floorPrice;
    String name;
    String picUrl;
}
