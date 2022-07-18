package com.cskaoyan.aspect;

import com.cskaoyan.util.ValidationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;


/**
 * 参数校验切面
 *
 * @author fanxing056
 * @date 2022/07/18 14:05
 */

@Component
@Aspect
public class ParamValidationAspect {

    // 通过自定义注解的切入点
    @Pointcut("@annotation(com.cskaoyan.anno.ParamValidation)")
    public void pointCutAnno() {
    }

    @Around("pointCutAnno()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        BindingResult bindingResult = (BindingResult) args[args.length - 1];

        // 返回错误信息
        ValidationUtils.validData(bindingResult);

        Object proceed = joinPoint.proceed();
        return proceed;
    }
}
