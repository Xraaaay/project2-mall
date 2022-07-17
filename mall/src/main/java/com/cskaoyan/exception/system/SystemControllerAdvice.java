package com.cskaoyan.exception.system;

import com.cskaoyan.bean.common.BaseRespVo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 系统管理模块异常处理器
 *
 * @author Xrw
 * @date 2022/7/16 21:13
 */
@RestControllerAdvice
public class SystemControllerAdvice {

    @ExceptionHandler(InvalidParamException.class)
    public BaseRespVo runtimeExceptionHandler(InvalidParamException exception) {
        return BaseRespVo.invalidParameter(exception.getMessage());
    }
}
