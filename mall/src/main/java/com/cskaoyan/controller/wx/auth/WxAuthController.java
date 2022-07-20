package com.cskaoyan.controller.wx.auth;

import com.cskaoyan.bean.admin.adminauth.InfoData;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.MarketUser;
import com.cskaoyan.bean.wx.wxcomment.UserInfo;
import com.cskaoyan.config.shiro.MarketToken;
import com.cskaoyan.exception.InvalidDataException;
import com.cskaoyan.service.wx.auth.AccountService;
import com.cskaoyan.util.Md5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author changyong
 * @since 2022/07/18 15:54
 */
@RestController
@RequestMapping("wx/auth")
public class WxAuthController {

    @Autowired
    AccountService accountService;

    /**
     * 整合Shiro之后的login
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo<com.cskaoyan.bean.admin.auth.LoginUserData>
     * @author Zah
     * @date 2022/07/17 22:10
     */
    @PostMapping("login")
    public BaseRespVo login(@RequestBody Map map) throws Exception {
        // 此处的username是手机号
        String username = (String) map.get("username");

        //正则验证
        if (!username.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$")) {
            throw new InvalidDataException("请输入正确的账户");
        }

        String originalPassword = (String) map.get("password");

        // 将密码通过MD5进行加密
        String password = Md5Utils.getMd5(originalPassword);

        // 认证登录准备
        Subject subject = SecurityUtils.getSubject();
        //UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        MarketToken token = new MarketToken(username, password, "wx");
        // 认证登录
        subject.login(token);

        boolean authenticated = subject.isAuthenticated();

        if (authenticated == true) {
            // 获取经过认证之后的用户信息
            MarketUser primaryPrincipal = (MarketUser) subject.getPrincipals().getPrimaryPrincipal();
            // 获取SessionId
            Serializable sessionId = subject.getSession().getId();

            Map<String, Object> wxLoginUser = new HashMap<>();
            wxLoginUser.put("token", sessionId);
            UserInfo userInfo = new UserInfo();
            userInfo.setAvatarUrl(primaryPrincipal.getAvatar());
            userInfo.setNickName(primaryPrincipal.getUsername());
            wxLoginUser.put("userInfo", userInfo);
            return BaseRespVo.ok(wxLoginUser);
        }
        throw new InvalidDataException("用户名或密码错误");
    }

    @RequestMapping("401")
    public static BaseRespVo unAuthc() {
        return new BaseRespVo(null, 501, "请登录");
    }

    // @RequestMapping("403")
    // public BaseRespVo noPermissiom() {
    //     return new BaseRespVo(null, 506, "无操作权限");
    // }



    @RequestMapping("index")
    public BaseRespVo index() {
        return BaseRespVo.ok(null);
    }

    @PostMapping("logout")
    public BaseRespVo logout() {
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

    /**
     * 发送验证码
     *
     * @param map
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author fanxing056
     * @date 2022/07/19 20:26
     */
    @PostMapping("/regCaptcha")
    public BaseRespVo regCaptcha(@RequestBody Map<String, String> map) {

        String mobile = map.get("mobile");

        // 正则验证
        if (!mobile.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$")) {
            throw new InvalidDataException("请输入正确的手机号码");
        }

        int code = accountService.regCaptcha(mobile);

        if (code == 200) {
            return BaseRespVo.ok(code);
        } else {
            return BaseRespVo.invalidData("已经发送验证码，请五分钟后再次获取");
        }
    }

    @PostMapping("/register")
    public BaseRespVo register(@RequestBody Map<String, String> map) throws Exception {

        // 正则验证
        String mobile = map.get("mobile");
        if (!mobile.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$")) {
            throw new InvalidDataException("请输入正确的手机号码");
        }
        // 注册
        int status = accountService.register(map);

        // 登录
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("username", mobile);
        hashMap.put("password", map.get("password"));
        return login(hashMap);
    }

    @PostMapping("/reset")
    public BaseRespVo reset(@RequestBody Map<String, String> map) throws Exception {
        // 正则验证
        String mobile = map.get("mobile");
        if (!mobile.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$")) {
            throw new InvalidDataException("请输入正确的手机号码");
        }

        int affect = accountService.reset(map);
        if (affect == 0) {
            throw new InvalidDataException("该账号不存在");
        }
        return BaseRespVo.ok(null);
    }

}
