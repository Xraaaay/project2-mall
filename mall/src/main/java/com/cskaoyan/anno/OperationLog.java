package com.cskaoyan.anno;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统管理模块：操作日志注解
 * @author Xrw
 * @date 2022/7/18 20:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {
    // 操作动作
    // @AliasFor("action")
    // String value() default "";

    // @AliasFor("value")
    // xrw 别名怎么不生效啊（╯' - ')╯︵ ┻━┻
    String action() default "";

    // 操作类型：安全操作1，订单操作2
    int type() default 1;
}
