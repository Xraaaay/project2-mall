package com.cskaoyan.controller.adminauth;

<<<<<<< HEAD

import com.cskaoyan.bean.adminauth.InfoData;
import com.cskaoyan.bean.adminauth.LoginUserData;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.service.adminauth.AdminLoginService;
=======
import com.cskaoyan.anno.SecurityOperationLog;
import com.cskaoyan.anno.SecurityOperationType;
import com.cskaoyan.bean.adminauth.AdminInfoBean;
import com.cskaoyan.bean.adminauth.InfoData;
import com.cskaoyan.bean.adminauth.LoginUserData;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.system.MarketAdmin;
import com.cskaoyan.config.shiro.MarketToken;
>>>>>>> ee00dd8c22fb15885c4805c45369e062eea65f02
import com.cskaoyan.util.Md5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//shiro整合之后，在做具体的开发
//响应结果都是JSON，Controller组件上我们都用@RestController注解
@RestController
@RequestMapping("admin/auth")
public class AuthController {

    @Autowired
    AdminLoginService adminLoginService;

    /**
     * 整合Shiro之后的login
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo<com.cskaoyan.bean.adminauth.LoginUserData>
     * @author Zah
     * @date 2022/07/17 22:10
     */
    // AOP 安全操作日志xrw
    @SecurityOperationLog(SecurityOperationType.LOGIN)
    @PostMapping("login")
    public BaseRespVo<LoginUserData> login(@RequestBody Map map) throws Exception {

        String username = (String) map.get("username");
        String originalPassword = (String) map.get("password");

        // 将密码通过MD5进行加密
        String password = Md5Utils.getMd5(originalPassword);

<<<<<<< HEAD
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        LoginUserData loginUserData = adminLoginService.adminLogin(token);

=======
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
>>>>>>> ee00dd8c22fb15885c4805c45369e062eea65f02
        return BaseRespVo.ok(loginUserData);
    }

    /**
     * 经登录认证之后的管理员信息
     *
     * @param token
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author Zah
     * @date 2022/07/18 14:22
     */
    @RequestMapping("info")
    public BaseRespVo info(String token) {

        InfoData infoData = adminLoginService.adminInfo();

        if (infoData == null) {
            return BaseRespVo.invalidData("未登录，请登录之后在操作");
        }

        return BaseRespVo.ok(infoData);
    }

<<<<<<< HEAD
    /**
     * 登出
     *
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author Zah
     * @date 2022/07/18 14:23
     */
=======
    // AOP 安全操作日志xrw
    @SecurityOperationLog(SecurityOperationType.LOGOUT)
>>>>>>> ee00dd8c22fb15885c4805c45369e062eea65f02
    @PostMapping("logout")
    public BaseRespVo logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseRespVo.ok(null);
    }

    @RequestMapping("401")
    public BaseRespVo unAuthc() {
        return new BaseRespVo(null, 501, "请登录");
    }

    @RequestMapping("403")
    public BaseRespVo noPermissiom() {
        return new BaseRespVo(null, 506, "无操作权限");
    }

    @RequestMapping("index")
    public BaseRespVo index() {
        return BaseRespVo.ok(null);
    }

}
