package com.zjx.security.browser;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <p>@ClassName: BrowserSecurityConfig </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/1 17:32</p>
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.httpBasic()//使用http默认登录方式
        http.formLogin()//使用表单登录方式
                .and()
                .authorizeRequests()//之后的配置都是授权配置
                .anyRequest()//对所有请求都验证权限
                .authenticated();//都需要身份认证
    }
}
