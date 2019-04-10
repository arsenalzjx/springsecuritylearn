package com.zjx.security.core.authorize;

import com.zjx.security.core.properties.SecurityConstants;
import com.zjx.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 安全模块配置
 * @author: zjx
 * @date: 2019/4/8 14:49
 */
@Component
@Order(Integer.MIN_VALUE)
public class ZjxAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
        //对跳转到登录页面的请求放行
        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                //对配置中自定义的登录页面进行放行
                securityProperties.getBrowser().getLoginPage(),
                //对手机登录提交请求放行
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                //OpenId登录进行放行
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID,
                //对图形验证码放行
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                //对注册页面放行
                securityProperties.getBrowser().getSignUpUrl(),
                //对session过期页面进行放行
                securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                //对退出登录成功页进行放行
                securityProperties.getBrowser().getSignOutUrl()
        ).permitAll();
    }
}
