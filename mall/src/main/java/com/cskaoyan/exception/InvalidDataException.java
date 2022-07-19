package com.cskaoyan.exception;

/**
 * 非法数据异常
 *
 * @author fanxing056
 * @date 2022/07/18 10:12
 */

public class InvalidDataException extends RuntimeException {
    public InvalidDataException() {
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
