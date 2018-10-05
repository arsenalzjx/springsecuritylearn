package com.zjx.security.browser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>@ClassName: BrowserSecurityConfig </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/1 17:32</p>
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.httpBasic()//使用http默认登录方式
        http.formLogin()//使用表单登录方式
                .loginPage("/zjx-signIn.html")//指定登录页面
                .loginProcessingUrl("/authentication/form")//指定登录提交表单请求
                .and()
                .authorizeRequests()//之后的配置都是授权配置
                .antMatchers("/zjx-signIn.html").permitAll()//使用匹配器将登录页面进行允许访问
                .anyRequest()//对所有请求都验证权限
                .authenticated()//都需要身份认证
                .and()
                .csrf().disable();//关闭csrf跨站请求伪造防护功能
    }
}
