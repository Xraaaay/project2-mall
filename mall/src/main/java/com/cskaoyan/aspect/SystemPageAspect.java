package com.cskaoyan.aspect;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * TODO XRW 系统管理模块：分页切面
 *
 * @author Xrw
 * @date 2022/7/16 16:47
 */
@Component
@Aspect
public class SystemPageAspect {

    @Pointcut("@annotation(com.cskaoyan.anno.SystemPage)")
    public void pagePointcut() {
    }

    @Around("pagePointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        // System.out.println(Arrays.toString(args));

        BasePageInfo info = (BasePageInfo) args[0];
        String name = (String) args[1];

        Object proceed = joinPoint.proceed();

        return proceed;
    }
}
