package com.cskaoyan.controller.adminauth;

import com.cskaoyan.anno.SecurityOperationLog;
import com.cskaoyan.anno.SecurityOperationType;
import com.cskaoyan.bean.adminauth.AdminInfoBean;
import com.cskaoyan.bean.adminauth.InfoData;
import com.cskaoyan.bean.adminauth.LoginUserData;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.system.MarketAdmin;
import com.cskaoyan.config.shiro.MarketToken;
import com.cskaoyan.util.Md5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

//shiro整合之后，在做具体的开发
//响应结果都是JSON，Controller组件上我们都用@RestController注解
@RestController
@RequestMapping("admin/auth")
public class AuthController {

    /**
     * 整合Shiro之后的login
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo<com.cskaoyan.bean.adminauth.LoginUserData>
     * @author Zah
     * @date 2022/07/17 22:10
     */
    // AOP 安全操作日志xrw
    @SecurityOperationLog(SecurityOperationType.LOGIN)
    @PostMapping("login")
    public BaseRespVo<LoginUserData> login(@RequestBody Map map) throws Exception {

        String username = (String)map.get("username");
        String originalPassword = (String)map.get("password");

        // 将密码通过MD5进行加密
        String password = Md5Utils.getMd5(originalPassword);

        // 认证登录准备
        Subject subject = SecurityUtils.getSubject();
        //UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        MarketToken token = new MarketToken(username, password, "admin");
        // 认证登录
        subject.login(token);

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
        return BaseRespVo.ok(loginUserData);
    }

    @RequestMapping("401")
    public BaseRespVo unAuthc(){
        return new BaseRespVo(null,501,"请登录");
    }

    @RequestMapping("403")
    public BaseRespVo noPermissiom() {
        return new BaseRespVo(null, 506, "无操作权限");
    }

    @RequestMapping("index")
    public BaseRespVo index() {
        return BaseRespVo.ok(null);
    }

    // AOP 安全操作日志xrw
    @SecurityOperationLog(SecurityOperationType.LOGOUT)
    @PostMapping("logout")
    public BaseRespVo logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseRespVo.ok(null);
    }

    @RequestMapping("info")
    public BaseRespVo info(String token) {


        //开发完shiro之后，再整合
        InfoData infoData = new InfoData();
        infoData.setName("admin123");

        //根据primaryPrincipal做查询
        infoData.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        ArrayList<String> roles = new ArrayList<>();
        roles.add("超级管理员");
        infoData.setRoles(roles);
        ArrayList<String> perms = new ArrayList<>();
        perms.add("*");
        infoData.setPerms(perms);


        return BaseRespVo.ok(infoData);
    }


}
