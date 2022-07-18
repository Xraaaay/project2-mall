package com.cskaoyan.config.shiro;

import com.cskaoyan.bean.system.MarketAdmin;
import com.cskaoyan.bean.system.MarketAdminExample;
import com.cskaoyan.mapper.system.MarketAdminMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Shiro的Realm
 * @author Zah
 * @date 2022/07/17 21:22
 */
@Component
public class MarketRealm extends AuthorizingRealm {

    @Autowired
    MarketAdminMapper marketAdminMapper;

    /**
     * Shiro的认证信息
     * @param authenticationToken
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @author Zah
     * @date 2022/07/17 21:24
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();

        // 根据用户名查询数据库中对应的管理员信息
        MarketAdminExample example = new MarketAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<MarketAdmin> marketAdmins = marketAdminMapper.selectByExample(example);
        if (marketAdmins.size() == 1){
            // 说明有一条数据是对应的
            MarketAdmin marketAdmin = marketAdmins.get(0);
            return new SimpleAuthenticationInfo(marketAdmin,marketAdmin.getPassword(),getName());
        }

        return null;
    }

    /**
     * Shiro的授权信息
     * @param principalCollection
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @author Zah
     * @date 2022/07/17 21:24
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        MarketAdmin principal = (MarketAdmin) principalCollection.getPrimaryPrincipal();

        // 根据用户信息查询出对应的权限列表
        //  List<String> getPermissions = getPermissionsByUsername(principal);

        // 把权限信息放入授权信息中
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // simpleAuthorizationInfo.addStringPermissions(getPermissions);

        return simpleAuthorizationInfo;
    }
}
