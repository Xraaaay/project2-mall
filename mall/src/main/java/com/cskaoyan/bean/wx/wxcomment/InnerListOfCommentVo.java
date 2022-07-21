package com.cskaoyan.bean.wx.wxcomment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 评论列表
 *
 * @author shn
 * @date 2022/07/18 22:34
 */
@Data
public class InnerListOfCommentVo<T> {
    T userInfo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date addTime;
    String[] picList;
    String adminContent;
    String content;

}
