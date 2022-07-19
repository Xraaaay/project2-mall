package com.cskaoyan.util;

import com.cskaoyan.bean.common.MarketUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 小程序 shiro获取用户信息
 *
 * @author shn
 * @date 2022/07/19 21:38
 */
public class GetUserInfoUtil {
    public static MarketUser getUserInfo(){
        Subject subject = SecurityUtils.getSubject();
        MarketUser principal = (MarketUser) subject.getPrincipals().getPrimaryPrincipal();
        return principal;
    }
}
