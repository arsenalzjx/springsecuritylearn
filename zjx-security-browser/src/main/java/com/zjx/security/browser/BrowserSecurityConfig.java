package com.zjx.security.browser;

import com.zjx.security.core.properties.SecurityProperties;
import com.zjx.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    //注入相关配置
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

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


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //初始化一个validateCodeFilter
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        //为validateCodeFilter设置初始化属性
        validateCodeFilter.setSecurityProperties(securityProperties);
        //为validateCodeFilter初始化过滤url
        validateCodeFilter.afterPropertiesSet();
        //http.httpBasic()//使用http默认登录方式
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()//使用表单登录方式
                    .loginPage("/authentication/require")//指定登录控制器
                    .loginProcessingUrl("/authentication/form")//指定登录提交表单请求
                    .successHandler(myAuthenticationSuccessHandler)//指定登录成功后的处理器
                    .failureHandler(myAuthenticationFailureHandler)//指定登录失败后的处理器
                    .and()
                .rememberMe()//开启配置rememberMe功能
                    .tokenRepository(persistentTokenRepository())//选择使用的tokenRepository
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//配置token过期时间
                    .userDetailsService(userDetailsService)//配置后续取得userdetail的服务
                    .and()
                .authorizeRequests()//之后的配置都是授权配置
                    .antMatchers("/authentication/require"//对跳转到登录页面的请求放行
                            , securityProperties.getBrowser().getLoginPage()//对配置中自定义的登录页面进行放行
                            , "/code/*")//对图形验证码放行
                    .permitAll()//使用匹配器将登录页面进行允许访问
                    .anyRequest()//对所有请求都验证权限
                    .authenticated()//都需要身份认证
                    .and()
                .csrf()
                    .disable();//关闭csrf跨站请求伪造防护功能
    }
}
