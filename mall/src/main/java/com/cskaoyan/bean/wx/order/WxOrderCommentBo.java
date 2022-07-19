package com.cskaoyan.bean.wx.order;

import lombok.Data;


/**
 * @author changyong
 * @since 2022/07/19 21:03
 */
@Data
public class WxOrderCommentBo {
    String content;
    boolean hasPicture;
    Integer orderGoodsId;
    String[] picUrls;
    Integer star;
}
