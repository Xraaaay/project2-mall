package com.cskaoyan.exception;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.controller.wx.auth.WxAuthController;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 通用异常处理器
 *
 * @author fanxing056
 * @date 2022/07/18 10:09
 */

@RestControllerAdvice
public class CommonControllerAdvice {
    @ExceptionHandler(InvalidParamException.class)
    public BaseRespVo invalidParameter(InvalidParamException exception) {
        return BaseRespVo.invalidParameter(exception.getMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    public BaseRespVo invalidData(InvalidDataException exception) {
        return BaseRespVo.invalidData(exception.getMessage());
    }

    @ExceptionHandler(UnAuthException.class)
    public BaseRespVo unAuth(UnAuthException exception) {
        // 重定向到登录界面
        return WxAuthController.unAuthc();
    }

    @ExceptionHandler({UnknownAccountException.class, IncorrectCredentialsException.class})
    public BaseRespVo invalidUsernamePassword(Exception exception) {
        return BaseRespVo.invalidParameter("用户名或账号密码不正确");
    }

    // 顶级异常处理
    // @ExceptionHandler(Throwable.class)
    public BaseRespVo commonException(Throwable throwable) {
        return BaseRespVo.invalidData("参数异常");
    }
}
