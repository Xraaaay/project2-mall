package com.cskaoyan.exception.common;

import com.cskaoyan.bean.common.BaseRespVo;
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

    // 顶级异常处理
    // @ExceptionHandler(Throwable.class)
    public BaseRespVo commonException(Throwable throwable) {
        return BaseRespVo.invalidData("参数异常");
    }
}
