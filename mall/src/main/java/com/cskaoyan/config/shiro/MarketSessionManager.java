package com.cskaoyan.config.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
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
    private static final String MARKET_ADMIN_TOKEN = "X-CskaoyanMarket-Admin-Token";

    // 后续还要添加小程序的SessionId的请求头
    private static final String MARKET_WX_TOKEN = "X-CskaoyanMarket-Token";
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

        // 禁止在url上拼接JSESSIONID
        request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED,this.isSessionIdUrlRewritingEnabled());

        String sessionId = request.getHeader(MARKET_ADMIN_TOKEN);
        if (sessionId != null && !"".equals(sessionId)){
            return sessionId;
        }
        String sessionId2 = request.getHeader(MARKET_WX_TOKEN);
        if (sessionId2 != null && !"".equals(sessionId2)){
            return sessionId2;
        }
        return super.getSessionId(servletRequest, response);

    }
}
