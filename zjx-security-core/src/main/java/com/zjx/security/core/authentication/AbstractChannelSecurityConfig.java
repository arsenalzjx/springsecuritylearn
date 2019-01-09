package com.zjx.security.core.authentication;

import com.zjx.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * @author: zjx
 * @date: 2018/10/18 15:50
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler myAuthenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        //使用表单登录方式
        http.formLogin()
                //指定进入登录页面的uri
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                //指定登录提交表单请求uri
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                //指定登录成功后的处理器
                .successHandler(myAuthenticationSuccessHandler)
                //指定登录失败后的处理器
                .failureHandler(myAuthenticationFailureHandler);
    }
}
