package com.cskaoyan.bean.wxcomment;

import com.cskaoyan.bean.MarketComment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 评论列表
 *
 * @author shn
 * @date 2022/07/18 22:34
 */
@Data
public class InnerListOfCommentVo {
    UserInfo userInfo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date addTime;
    String[] picList;
    String adminContent;
    String content;

}
