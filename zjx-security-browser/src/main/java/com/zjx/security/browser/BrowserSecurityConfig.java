package com.zjx.security.browser;

import com.zjx.security.core.authentication.AbstractChannelSecurityConfig;
import com.zjx.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.zjx.security.core.authorize.AuthorizeConfigManager;
import com.zjx.security.core.properties.SecurityProperties;
import com.zjx.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * BS架构中关于SpringSecurity的配置类
 * @author: zjx
 * @date: 2018/10/1 17:32
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig{

    /**
     * 注入相关配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    /**
     * 注入关于登录相关的服务
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 注入验证码过滤器相应配置
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /**
     * 注入启用短信验证码相关登录方式配置
     */
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /**
     * 注入社交配置类
     */
    @Autowired
    private SpringSocialConfigurer zjxSocialSecurityConfig;

    /**
     * session并发处理策略
     */
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    /**
     * session过期处理策略
     */
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置使用表单账号密码登录
        applyPasswordAuthenticationConfig(http);
        //http.httpBasic()使用http默认登录方式
        //应用对应的验证码过滤器配置
        http.apply(validateCodeSecurityConfig)
                //启用短信验证码登录方式
                .and().apply(smsCodeAuthenticationSecurityConfig)
                //启用社交配置,将社交配置加到过滤器链上
                .and().apply(zjxSocialSecurityConfig)
                //开启配置rememberMe功能
                .and().rememberMe()
                    //选择使用的tokenRepository
                    .tokenRepository(persistentTokenRepository())
                    //配置token过期时间
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    //配置后续取得userdetail的服务
                    .userDetailsService(userDetailsService)
                //开启session管理功能配置
                .and().sessionManagement()
                    //配置session过期处理策略
                    .invalidSessionStrategy(invalidSessionStrategy)
                    //一个账号的最大登录数
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                    //session并发策略配置,当后一个用户挤掉前一个用户时的策略操作
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    //当达到最大登录数时,是否阻止后续登录
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().getMaxSessionPreventsLogin())
                    .and()
                //开启退出相关配置
                .and().logout()
                    //默认为/logOut
                    .logoutUrl("/signOut")
                    //与logoutSuccessHandler()互斥,只能有一个生效,处理退出成功后需要做的操作
                    //.logoutSuccessUrl("/zjx-logout.html")
                    //指定退出成功处理器
                    .logoutSuccessHandler(logoutSuccessHandler)
                    //指定删除相关cookies
                    .deleteCookies("JSESSIONID")
                .and()
                    //关闭csrf跨站请求伪造防护功能
                    .csrf().disable();
        //应用授权配置
        authorizeConfigManager.config(http.authorizeRequests());
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setCreateTableOnStartup(true);
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
