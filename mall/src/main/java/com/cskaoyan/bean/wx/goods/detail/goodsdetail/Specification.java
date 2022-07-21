package com.cskaoyan.bean.wx.goods.detail.goodsdetail;

import com.cskaoyan.bean.common.MarketGoodsSpecification;
import lombok.Data;

import java.util.List;

/**
 * 
 * @date 2022/07/21 09:40
 * @author fanxing056
 */

@Data
public class Specification {

    private List<MarketGoodsSpecification> valueList;
    private String name;
}
