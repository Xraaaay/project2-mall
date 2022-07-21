package com.cskaoyan.bean.wx.goods.detail.goodsdetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 
 * @date 2022/07/21 09:54
 * @author fanxing056
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketCommentVo {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;
    private String adminContent;
    private String avatar;
    private String content;
    private Integer id;
    private String nickname;
    private String[] picList;
}
