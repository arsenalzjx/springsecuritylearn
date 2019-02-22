package com.zjx.security.browser;

import com.zjx.security.browser.logout.ZjxLogoutSuccessHandler;
import com.zjx.security.browser.session.ZjxExpiredSessionStrategy;
import com.zjx.security.browser.session.ZjxInvalidSessionStrategy;
import com.zjx.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 关于浏览器相关的自动配置
 * @author: zjx
 * @date: 2019/2/18 14:41
 */
@Configuration
public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;


    /**
     * session过期策略的默认配置,用户自定义时只需向容器中注入InvalidSessionStrategy的Bean则可覆盖默认配置
     * @author: zjx
     * @date 14:43 2019/2/18
     * @return org.springframework.security.web.session.InvalidSessionStrategy
     **/
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new ZjxInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * session并发登录策略的默认配置,用户自定义时只需向容器中注入sessionInformationExpiredStrategy的Bean
     * 则可覆盖默认配置
     * @author: zjx
     * @date 14:45 2019/2/18
     * @return org.springframework.security.web.session.SessionInformationExpiredStrategy
     **/
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new ZjxExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * 退出登录处理器相关配置,用户自定义时只需向容器中注入LogoutSuccessHandler的Bean
     * @author: zjx
     * @date 14:51 2019/2/22
     * @return org.springframework.security.web.authentication.logout.LogoutSuccessHandler
     **/
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new ZjxLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    }
}
