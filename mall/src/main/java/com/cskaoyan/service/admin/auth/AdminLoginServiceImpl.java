package com.cskaoyan.service.admin.auth;

import com.cskaoyan.bean.admin.adminauth.AdminInfoBean;
import com.cskaoyan.bean.admin.adminauth.InfoData;
import com.cskaoyan.bean.admin.adminauth.LoginUserData;
import com.cskaoyan.bean.admin.system.*;
import com.cskaoyan.mapper.system.MarketPermissionMapper;
import com.cskaoyan.mapper.system.MarketRoleMapper;
import com.cskaoyan.mapper.system.MarketRolePermissionMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录信息
 * @author Zah
 * @date 2022/07/18 14:01
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    MarketRoleMapper marketRoleMapper;
    @Autowired
    MarketPermissionMapper marketPermissionMapper;
    @Autowired
    MarketRolePermissionMapper marketRolePermissionMapper;

    /**
     * 后台管理员登录
     * @param token
     * @return com.cskaoyan.bean.admin.auth.LoginUserData
     * @author Zah
     * @date 2022/07/18 14:01
     */
    @Override
    public LoginUserData adminLogin(UsernamePasswordToken token) {

        // 获取操作的主体
        Subject subject = SecurityUtils.getSubject();

        // 认证登录
        subject.login(token);

        // 认证成功就会返回true
        boolean authenticated = subject.isAuthenticated();

        // 获取经过认证之后的用户信息
        MarketAdmin primaryPrincipal = (MarketAdmin) subject.getPrincipals().getPrimaryPrincipal();

        // 获取SessionId
        Serializable sessionId = subject.getSession().getId();

        LoginUserData loginUserData = new LoginUserData();
        AdminInfoBean adminInfo = new AdminInfoBean();
        adminInfo.setAvatar(primaryPrincipal.getAvatar());
        adminInfo.setNickName(primaryPrincipal.getUsername());
        loginUserData.setAdminInfo(adminInfo);
        loginUserData.setToken((String) sessionId);

        return loginUserData;
    }

    /**
     * 管理员登录之后所带的信息
     * @return com.cskaoyan.bean.admin.auth.InfoData
     * @author Zah
     * @date 2022/07/18 14:21
     */
    @Override
    public InfoData adminInfo(){

        Subject subject = SecurityUtils.getSubject();

        if (subject.getPrincipals() == null){
            subject.logout();
            return null;
        }

        MarketAdmin primaryPrincipal = (MarketAdmin) subject.getPrincipals().getPrimaryPrincipal();

        // 根据rolesId获取role表中的name
        MarketRoleExample roleExample = new MarketRoleExample();
        roleExample.createCriteria().andIdEqualTo(primaryPrincipal.getRoleIds()[0]);
        List<MarketRole> marketRoles = marketRoleMapper.selectByExample(roleExample);

        // 根据rolesId获取permission表中的permission
        MarketPermissionExample permExample = new MarketPermissionExample();
        permExample.createCriteria().andRoleIdEqualTo(primaryPrincipal.getRoleIds()[0]);
        List<MarketPermission> marketPermissions = marketPermissionMapper.selectByExample(permExample);

        // 记录该管理员应该有的权限
        ArrayList<String> perms = new ArrayList<>();
        for (MarketPermission marketPermission : marketPermissions) {
            // 再根据permission表的permission去role-permission表中找到api()
            MarketRolePermissionExample rpExample = new MarketRolePermissionExample();
            rpExample.createCriteria().andIdEqualTo(marketPermission.getPermission());
            List<MarketRolePermission> mrps = marketRolePermissionMapper.selectByExample(rpExample);
            for (MarketRolePermission mrp : mrps) {
                perms.add(mrp.getApi());
            }
        }

        InfoData infoData = new InfoData();

        ArrayList<String> roles = new ArrayList<>();
        for (MarketRole marketRole : marketRoles) {
            roles.add(marketRole.getName());
        }
        infoData.setRoles(roles);

        infoData.setPerms(perms);

        infoData.setName(primaryPrincipal.getUsername());

        infoData.setAvatar(primaryPrincipal.getAvatar());

        return infoData;
    }
}
