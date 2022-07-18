package com.cskaoyan.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统管理模块：操作日志注解
 * 订单操作
 *
 * @author Xrw
 * @date 2022/7/18 10:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderOperationLog {
    OrderOperationType value();
}
