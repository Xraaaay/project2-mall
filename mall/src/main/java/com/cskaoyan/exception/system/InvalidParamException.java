package com.cskaoyan.exception.system;

/**
 * 参数非法异常
 * @author Xrw
 * @date 2022/7/17 16:00
 */
public class InvalidParamException extends RuntimeException {
    public InvalidParamException(String message) {
        super(message);
    }

    public InvalidParamException() {
    }
}
