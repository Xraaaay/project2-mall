package com.cskaoyan.config.shiro;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * shiro的组件注册及其组件之间的依赖关系
 *
 * @author Zah
 * @date 2022/07/17 21:33
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 提供Filter链
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/admin/auth/login","anon");
        filterChainDefinitionMap.put("/admin/auth/info","anon");
        filterChainDefinitionMap.put("/admin/auth/401","anon");

        // admin开头的请求,都要经过认证才行(等待开发完成在做拦截，把anon改成authc)
        filterChainDefinitionMap.put("/admin/**","anon");
        filterChainDefinitionMap.put("/wx/**","anon");

        // 对于没有访问权限的重定向地址
        shiroFilterFactoryBean.setLoginUrl("/admin/auth/401");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(MarketRealm realm,
                                                     MarketSessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealms(Arrays.asList(realm));
        return securityManager;
    }

    /**
     * 注册组件,使得权限与handler方法进行绑定 (与授权有关，也未用到)
     * @param securityManager
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @author Zah
     * @date 2022/07/17 21:44
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor
    authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();

        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);

        return authorizationAttributeSourceAdvisor;
    }

}
