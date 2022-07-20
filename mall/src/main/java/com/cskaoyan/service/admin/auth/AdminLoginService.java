package com.cskaoyan.service.admin.auth;

import com.cskaoyan.bean.admin.adminauth.InfoData;
import com.cskaoyan.bean.admin.adminauth.LoginUserData;
import org.apache.shiro.authc.UsernamePasswordToken;

public interface AdminLoginService {

    /**
     * 用户登录 
     * @param token 
     * @return com.cskaoyan.bean.admin.adminauth.LoginUserData
     * @author Zah
     * @date 2022/07/20 10:48 
     */
    LoginUserData adminLogin(UsernamePasswordToken token);

    /**
     * 用户登录携带的信息 
     * @return com.cskaoyan.bean.admin.adminauth.InfoData
     * @author Zah
     * @date 2022/07/20 10:48 
     */
    InfoData adminInfo();
}

