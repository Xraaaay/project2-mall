package com.cskaoyan.service.admin.auth;

import com.cskaoyan.bean.admin.adminauth.InfoData;
import com.cskaoyan.bean.admin.adminauth.LoginUserData;
import org.apache.shiro.authc.UsernamePasswordToken;

public interface AdminLoginService {

    LoginUserData adminLogin(UsernamePasswordToken token);

    InfoData adminInfo();
}
