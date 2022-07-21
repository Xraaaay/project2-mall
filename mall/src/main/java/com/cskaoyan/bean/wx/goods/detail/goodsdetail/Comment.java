package com.cskaoyan.bean.wx.goods.detail.goodsdetail;

import lombok.Data;

import java.util.List;

/**
 * 
 * @date 2022/07/21 09:49
 * @author fanxing056
 */

@Data
public class Comment {

    private List<MarketCommentVo> data;
    private int count;
}
