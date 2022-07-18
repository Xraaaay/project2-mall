package com.cskaoyan.service.adminauth;

import com.cskaoyan.bean.adminauth.InfoData;
import com.cskaoyan.bean.adminauth.LoginUserData;
import org.apache.shiro.authc.UsernamePasswordToken;

public interface AdminLoginService {

    LoginUserData adminLogin(UsernamePasswordToken token);

    InfoData adminInfo();
}
