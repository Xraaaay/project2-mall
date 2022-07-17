package com.cskaoyan.config.shiro;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Shiro的SessionManager
 * 保证跨域场景下的Session一致问题
 * @author Zah
 * @date 2022/07/17 21:25
 */
@Component
public class MarketSessionManager extends DefaultWebSessionManager {

    // 认证完后之后，把SessionId作为响应结果相应给前端，前端发送请求，通过请求头携带了SessionId
    private static final String HEADER = "X-CskaoyanMarket-Admin-Token";

    // 后续还要添加小程序的SessionId的请求头

    /**
     * 获得SessionId
     * @param servletRequest
     * @param response
     * @return java.io.Serializable
     * @author Zah
     * @date 2022/07/17 21:26
     */
    @Override
    protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse response) {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String sessionId = request.getHeader(HEADER);
        if (sessionId != null && !"".equals(sessionId)){
            return sessionId;
        }
        return super.getSessionId(servletRequest, response);

    }
}
