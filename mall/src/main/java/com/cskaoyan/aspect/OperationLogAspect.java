package com.cskaoyan.aspect;

import com.cskaoyan.bean.system.MarketLog;
import com.cskaoyan.mapper.system.MarketLogMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Pointcut("@annotation(com.cskaoyan.anno.OperationLog)")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MarketLog log = new MarketLog();
        log.setAddTime(new Date());
        log.setUpdateTime(new Date());

        // TODO XRW 获取admin,ip,type,status

        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.setResult("失败");
            logMapper.insertSelective(log);
            throw throwable;
        }

        log.setResult("成功");
        logMapper.insertSelective(log);

        return proceed;
    }
}
