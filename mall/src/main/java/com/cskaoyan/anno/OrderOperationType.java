package com.cskaoyan.anno;

/**
 * 订单操作的操作动作
 *
 * @author Xrw
 * @date 2022/7/18 10:40
 */
public enum OrderOperationType {
    SHIP("订单发货"),
    DELETE("删除订单");

    public final String action;

    OrderOperationType(String action) {
        this.action = action;
    }
}
