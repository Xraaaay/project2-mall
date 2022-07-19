package com.cskaoyan.bean.wx.wxcomment;

/**
 * 小程序评论列表
 *
 * @author shn
 * @date 2022/07/18 21:11
 */

public class WXCommentVo {

    /**
     * hasPicCount : 1
     * allCount : 2
     */
    private long hasPicCount;
    private long allCount;

    public void setHasPicCount(long hasPicCount) {
        this.hasPicCount = hasPicCount;
    }

    public void setAllCount(long allCount) {
        this.allCount = allCount;
    }

    public long getHasPicCount() {
        return hasPicCount;
    }

    public long getAllCount() {
        return allCount;
    }
}
