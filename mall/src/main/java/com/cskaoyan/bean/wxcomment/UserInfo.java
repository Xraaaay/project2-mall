package com.cskaoyan.bean.wxcomment;

/**
 * comment里的list中的userInfo
 *
 * @author shn
 * @date 2022/07/18 22:39
 */
public class UserInfo {

    /**
     * avatarUrl :
     * nickName : user123
     */
    private String nickName;
    private String avatarUrl;

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }
}
