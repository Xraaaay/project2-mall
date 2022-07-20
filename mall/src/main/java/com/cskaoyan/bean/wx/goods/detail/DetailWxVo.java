package com.cskaoyan.bean.wx.goods.detail;

import com.cskaoyan.bean.admin.goods.vo.MarketGoodsVo;
import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.wxcomment.WXCommentVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pqk
 * @since 2022/07/20 14:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailWxVo {
    List<SpecificationList> specificationList;
    List<String> groupon = null;
    List<MarketIssue> issue;
    boolean userHasCollect;
    String shareImage =null;
    CommentVo comment;
    boolean share =true;
    List<MarketGoodsAttribute> attribute;
    MarketBrand brand;
    List<MarketGoodsProduct> productList;
    MarketGoodsVo info;
}
