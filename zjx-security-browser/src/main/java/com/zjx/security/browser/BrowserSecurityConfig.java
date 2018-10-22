package com.zjx.security.browser;

import com.zjx.security.core.authentication.AbstractChannelSecurityConfig;
import com.zjx.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.zjx.security.core.properties.SecurityConstants;
import com.zjx.security.core.properties.SecurityProperties;
import com.zjx.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * <p>@ClassName: BrowserSecurityConfig </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/1 17:32</p>
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig{

    //注入相关配置
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //配置使用表单账号密码登录
        applyPasswordAuthenticationConfig(http);
        //http.httpBasic()//使用http默认登录方式
        //应用对应的验证码过滤器配置
        http.apply(validateCodeSecurityConfig)
                .and()
                //启用短信验证码登录方式
                    .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                    .rememberMe()//开启配置rememberMe功能
                    .tokenRepository(persistentTokenRepository())//选择使用的tokenRepository
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//配置token过期时间
                    .userDetailsService(userDetailsService)//配置后续取得userdetail的服务
                .and()
                    .authorizeRequests()//之后的配置都是授权配置
                        .antMatchers(
                                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,//对跳转到登录页面的请求放行
                                securityProperties.getBrowser().getLoginPage(),//对配置中自定义的登录页面进行放行
                                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,//对手机登录提交请求放行
                                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*")//对图形验证码放行
                        .permitAll()//使用匹配器将登录页面进行允许访问
                    .anyRequest()//对所有请求都验证权限
                    .authenticated()//都需要身份认证
                .and()
                    .csrf()
                    .disable();//关闭csrf跨站请求伪造防护功能
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setCreateTableOnStartup(true);
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
