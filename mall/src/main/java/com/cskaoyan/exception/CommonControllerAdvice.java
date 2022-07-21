package com.cskaoyan.exception;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.controller.wx.auth.WxAuthController;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
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

    /**
     * 无认证，没有权限异常
     * @param exception
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author Zah
     * @date 2022/07/21 17:12
     */
    @ExceptionHandler(UnAuthException.class)
    public BaseRespVo unAuth(UnAuthException exception) {
        // 跳转到登录界面
        return WxAuthController.unAuthc();
    }

    /**
     * 登录时账户和密码的异常
     * @param exception
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author Zah
     * @date 2022/07/21 17:10
     */
    @ExceptionHandler({UnknownAccountException.class, IncorrectCredentialsException.class})
    public BaseRespVo invalidUsernamePassword(Exception exception) {
        return BaseRespVo.invalidParameter("用户名或账号密码不正确");
    }

    // 顶级异常处理
    // @ExceptionHandler(Throwable.class)
    public BaseRespVo commonException(Throwable throwable) {
        return BaseRespVo.invalidData("参数异常");
    }

    /**
     * 无授权操作异常
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author Zah
     * @date 2022/07/21 17:10
     */
    @ExceptionHandler(AuthorizationException.class)
    public BaseRespVo noPermission(){
        return BaseRespVo.noPermission();
    }
}
