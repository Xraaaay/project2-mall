package com.cskaoyan.exception.auth;

import com.cskaoyan.bean.common.BaseRespVo;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 认证信息出现的异常处理
 *
 * @author Zah
 * @date 2022/07/17 22:11
 */
@RestControllerAdvice
public class AuthException {

    @ExceptionHandler({UnknownAccountException.class, IncorrectCredentialsException.class})
    public BaseRespVo exception(Exception exception) {
        return new BaseRespVo(null, 605, "用户名或账号密码不正确");
    }
}
