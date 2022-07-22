package com.cskaoyan.aspect;

import com.cskaoyan.anno.OperationLog;
import com.cskaoyan.bean.common.MarketOrder;
import com.cskaoyan.bean.admin.mallmanagement.bo.MarketOrderShipBo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.system.*;
import com.cskaoyan.mapper.common.MarketOrderMapper;
import com.cskaoyan.mapper.system.MarketLogMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

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

    @Autowired
    MarketOrderMapper orderMapper;

    @Pointcut("@annotation(com.cskaoyan.anno.OperationLog)")
    public void logPointcut() {
    }

    @Transactional
    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MarketLog log = new MarketLog();
        log.setAddTime(new Date());
        log.setUpdateTime(new Date());

        // 通过反射获取注解中的type和action
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationLog annotation = method.getAnnotation(OperationLog.class);
        int type = annotation.type();
        String action = annotation.action();
        log.setType(type);
        log.setAction(action);

        // 通过request获取ip
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String ip = request.getRemoteHost();
        log.setIp(ip);

        // 获取管理员信息logout
        MarketAdmin marketAdmin = null;
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        if (principals != null) {
            marketAdmin = (MarketAdmin) principals.getPrimaryPrincipal();
            String username = marketAdmin.getUsername();
            log.setAdmin(username);
        }

        BaseRespVo proceed = null;
        try {
            proceed = (BaseRespVo) joinPoint.proceed();
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            log.setAdmin("匿名用户");
            proceed = BaseRespVo.invalidParameter("用户名或账号密码不正确");
        }
        String errmsg = proceed.getErrmsg();
        int errno = proceed.getErrno();
        Object data = proceed.getData();

        // 获取管理员信息login
        if (principals == null) {
            principals = subject.getPrincipals();
            if (principals != null) {
                marketAdmin = (MarketAdmin) principals.getPrimaryPrincipal();
                String username = marketAdmin.getUsername();
                log.setAdmin(username);
            } else {
                log.setAdmin("匿名用户");
            }
        }

        // 操作状态：失败
        if (errno != 0) {
            log.setStatus(false);
            log.setResult(errmsg);
            logMapper.insertSelective(log);
            return proceed;
        }

        // 操作状态：成功
        // 获取操作结果
        Object[] args = joinPoint.getArgs();
        if ("添加管理员".equals(action)) {
            // 操作结果：添加管理员名称
            MarketAdminCreateVo createVo = (MarketAdminCreateVo) data;
            log.setResult(createVo.getUsername());
        } else if ("编辑管理员".equals(action)) {
            // 操作结果：编辑管理员新的名称
            MarketAdminUpdateVo updateVo = (MarketAdminUpdateVo) data;
            log.setResult(updateVo.getUsername());
        } else if ("删除管理员".equals(action)) {
            // 操作结果：删除管理员名称
            MarketAdmin deleteAdmin = (MarketAdmin) args[0];
            log.setResult(deleteAdmin.getUsername());
        } else if ("订单发货".equals(action)) {
            // 操作结果：订单编号
            MarketOrderShipBo shipBo = (MarketOrderShipBo) args[0];
            Integer orderId = shipBo.getOrderId();
            MarketOrder marketOrder = orderMapper.selectByPrimaryKey(orderId);
            log.setResult("订单编号：" + marketOrder.getOrderSn());
        } else if ("删除订单".equals(action)) {
            // 操作结果：订单编号
            Map map = (Map) args[0];
            Integer orderId = (Integer) map.get("orderId");
            MarketOrder marketOrder = orderMapper.selectByPrimaryKey(orderId);
            log.setResult("订单编号：" + marketOrder.getOrderSn());
        }

        log.setStatus(true);
        logMapper.insertSelective(log);

        return proceed;
    }
}
