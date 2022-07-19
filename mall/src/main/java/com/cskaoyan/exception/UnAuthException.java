package com.cskaoyan.exception;

/**
 * 用户sessionId失效
 *
 * @author Xrw
 * @date 2022/7/19 22:38
 */
public class UnAuthException extends RuntimeException {
    public UnAuthException() {
    }

    public UnAuthException(String message) {
        super(message);
    }
}
