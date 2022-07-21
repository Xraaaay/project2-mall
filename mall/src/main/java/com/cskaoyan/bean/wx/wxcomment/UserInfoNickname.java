package com.cskaoyan.bean.wx.wxcomment;

import lombok.Data;

/**
 * 解决 nickName问题
 *
 * @author shn
 * @date 2022/07/21 16:20
 */
@Data
public class UserInfoNickname {
    private String nickname;
    private String avatarUrl;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
