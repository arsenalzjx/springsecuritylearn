package com.zjx.security;

import com.zjx.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author: zjx
 * @date: 2019/4/8 16:09
 */
@Component
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(HttpMethod.GET,"/user")
                .hasRole("ADMIN");
    }
}
