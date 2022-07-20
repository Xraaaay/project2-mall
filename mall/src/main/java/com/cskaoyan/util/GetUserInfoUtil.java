package com.cskaoyan.util;

import com.cskaoyan.bean.common.MarketIssue;
import com.cskaoyan.bean.common.MarketUser;
import com.cskaoyan.exception.UnAuthException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
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
        PrincipalCollection principals = subject.getPrincipals();
        if (principals==null) {
            //session失效，重定向
            subject.logout();
            throw new UnAuthException();
        }
        MarketUser principal = (MarketUser) principals.getPrimaryPrincipal();
        return principal;
    }

}
