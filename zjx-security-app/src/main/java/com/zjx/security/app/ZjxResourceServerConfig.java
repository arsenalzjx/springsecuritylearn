package com.zjx.security.app;

import com.zjx.security.app.social.openid.OpenIdAuthenticationSecurityConfig;
import com.zjx.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.zjx.security.core.authorize.AuthorizeConfigManager;
import com.zjx.security.core.properties.SecurityConstants;
import com.zjx.security.core.properties.SecurityProperties;
import com.zjx.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 *
 * @author: zjx
 * @date: 2019/2/28 12:46
 */
@Configuration
@EnableResourceServer
public class ZjxResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler myAuthenticationFailureHandler;

    /**
     * 注入启用短信验证码相关登录方式配置
     */
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /**
     * 注入社交配置类
     */
    @Autowired
    private SpringSocialConfigurer zjxSocialSecurityConfig;

    /**
     * 注入相关配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 注入OpenId登录相关安全配置
     */
    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        //使用表单登录方式
        http.formLogin()
                //指定进入登录页面的url
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                //指定登录提交表单请求url
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                //指定登录成功后的处理器
                .successHandler(myAuthenticationSuccessHandler)
                //指定登录失败后的处理器
                .failureHandler(myAuthenticationFailureHandler);

        //http.httpBasic()使用http默认登录方式
        //应用对应的验证码过滤器配置
        http.apply(validateCodeSecurityConfig)
                //启用短信验证码登录方式
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                //启用社交配置,将社交配置加到过滤器链上
                .and().apply(zjxSocialSecurityConfig)
                //启用openId方式登录配置
                .and().apply(openIdAuthenticationSecurityConfig)
                .and()
                //关闭csrf跨站请求伪造防护功能
                .csrf().disable();
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
