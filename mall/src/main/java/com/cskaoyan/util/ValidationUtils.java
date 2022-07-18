package com.cskaoyan.util;

import com.cskaoyan.exception.common.InvalidDataException;
import com.cskaoyan.exception.common.InvalidParamException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 参数校验工具类
 *
 * @author stone
 * @date 2022/07/18 10:03
 */

public class ValidationUtils {

    public static void validParam(BindingResult bindingResult) throws InvalidParamException {
        if (bindingResult.hasFieldErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            //请求参数名
            String field = fieldError.getField();
            //错误的值
            Object rejectedValue = fieldError.getRejectedValue();
            //错误的消息
            String defaultMessage = fieldError.getDefaultMessage();
            String msg = "请求参数" + field + "没有通过校验：值为" + rejectedValue + ";" + defaultMessage;
            throw new InvalidParamException(msg);
        }
    }

    public static void validData(BindingResult bindingResult) throws InvalidDataException {
        if (bindingResult.hasFieldErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            //错误的消息
            String msg = fieldError.getDefaultMessage();
            throw new InvalidParamException(msg);
        }
    }
}
