package com.zjx.security.browser;

import com.zjx.security.browser.session.ZjxExpiredSessionStrategy;
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
                    //配置session失效时跳转的地址
                    .invalidSessionUrl("/session/invalid")
                    //一个账号的最大登录数
                    .maximumSessions(1)
                    //配置当后一个用户挤掉前一个用户时的策略操作
                    .expiredSessionStrategy(new ZjxExpiredSessionStrategy())
                    //当达到最大登录数时,是否阻止后续登录
                    .maxSessionsPreventsLogin(true)
                    .and()
                //开启授权配置
                .and().authorizeRequests()
                    .antMatchers(
                            //对跳转到登录页面的请求放行
                            SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                            //对配置中自定义的登录页面进行放行
                            securityProperties.getBrowser().getLoginPage(),
                            //对手机登录提交请求放行
                            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                            //对图形验证码放行
                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                            //对注册页面放行
                            securityProperties.getBrowser().getSignUpUrl(),
                            "/user/regist","/session/invalid")
                        //使用匹配器将登录页面进行允许访问
                        .permitAll()
                    //对所有请求都验证权限
                    .anyRequest()
                    //都需要身份认证
                    .authenticated()
                .and()
                    //关闭csrf跨站请求伪造防护功能
                    .csrf().disable();
    }


    /**
     * 注册一个加密器到容器中
     * @author: zjx
     * @date 17:31 2019/1/4
     * @return org.springframework.security.crypto.password.PasswordEncoder
     **/
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
