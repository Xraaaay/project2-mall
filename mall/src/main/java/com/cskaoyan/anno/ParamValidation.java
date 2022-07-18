package com.cskaoyan.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解用于给指定controller层方法做增强
 * 参数校验
 *
 * @author fanxing056
 * @date 2022/07/18 14:04
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamValidation {
}
