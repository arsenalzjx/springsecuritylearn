package com.zjx.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权代理配置接口,为其他模块提供自定义配置接口
 * @author: zjx
 * @date: 2019/4/8 14:45
 */
public interface AuthorizeConfigProvider {
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
