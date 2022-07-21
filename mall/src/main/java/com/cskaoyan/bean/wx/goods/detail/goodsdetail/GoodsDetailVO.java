package com.cskaoyan.bean.wx.goods.detail.goodsdetail;

import com.cskaoyan.bean.common.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 
 * @date 2022/07/21 09:37
 * @author fanxing056
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailVO {

    private List<Specification> specificationList;
    private List<MarketGrouponRules> groupon;
    private List<MarketIssue> issue;
    private int userHasCollect;
    private String shareImage;
    private Comment comment;
    private boolean share;
    private List<MarketGoodsAttribute> attribute;
    private MarketBrand brand;
    private List<MarketGoodsProduct> productList;
    private MarketGoods info;
}
