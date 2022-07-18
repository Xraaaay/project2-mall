package com.cskaoyan.aspect;

import com.cskaoyan.anno.OrderOperationLog;
import com.cskaoyan.anno.SecurityOperationLog;
import com.cskaoyan.bean.system.MarketAdmin;
import com.cskaoyan.bean.system.MarketLog;
import com.cskaoyan.mapper.system.MarketLogMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统管理模块：操作日志
 *
 * @author Xrw
 * @date 2022/7/17 20:37
 */
@Component
@Aspect
public class OperationLogAspect {

    @Autowired
    MarketLogMapper logMapper;

    @Pointcut("@annotation(com.cskaoyan.anno.SecurityOperationLog)")
    public void securityPointcut() {
    }

    @Pointcut("@annotation(com.cskaoyan.anno.OrderOperationLog)")
    public void orderPointcut() {
    }

    @Around("securityPointcut() || orderPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MarketLog log = new MarketLog();
        log.setAddTime(new Date());
        log.setUpdateTime(new Date());

        // 通过反射获取注解中的操作类别type和操作动作action
        // 获取方法的注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[] annotations = method.getDeclaredAnnotations();
        // 根据注解进行不同的赋值
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(SecurityOperationLog.class)) {
                log.setType(1);
                SecurityOperationLog security = (SecurityOperationLog) annotation;
                String action = security.value().action;
                log.setAction(action);
            } else if (annotation.annotationType().equals(OrderOperationLog.class)) {
                log.setType(2);
                OrderOperationLog order = (OrderOperationLog) annotation;
                String action = order.value().action;
                log.setAction(action);
            }
        }

        // 通过request获取ip
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String ip = request.getRemoteHost();
        log.setIp(ip);

        // 获取管理员信息login
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        if (principals != null) {
            MarketAdmin marketAdmin = (MarketAdmin) principals.getPrimaryPrincipal();
            String username = marketAdmin.getUsername();
            log.setAdmin(username);
        }

        Object proceed = null;
        try {
            // 执行操作
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            // 操作状态：失败
            log.setStatus(false);
            log.setResult(throwable.getMessage());
            logMapper.insertSelective(log);
            throw throwable;
        }

        // 获取管理员信息logout
        principals = subject.getPrincipals();
        if (principals != null) {
            MarketAdmin marketAdmin = (MarketAdmin) principals.getPrimaryPrincipal();
            String username = marketAdmin.getUsername();
            log.setAdmin(username);
        }

        // 操作状态：成功
        log.setStatus(true);
        logMapper.insertSelective(log);

        return proceed;
    }
}
