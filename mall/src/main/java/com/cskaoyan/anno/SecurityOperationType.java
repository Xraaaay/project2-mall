package com.cskaoyan.anno;

/**
 * 安全操作的操作动作
 *
 * @author Xrw
 * @date 2022/7/18 10:17
 */
public enum SecurityOperationType {
    LOGIN("登录"),
    LOGOUT("退出"),
    CREAT("添加管理员"),
    UPDATE("编辑管理员"),
    DELETE("删除管理员");

    public final String action;

    private SecurityOperationType(String action) {
        this.action = action;
    }
}
