package com.zjx.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author: zjx
 * @date: 2019/3/18 13:27
 */
public interface SocialAuthenticationFilterPostProcessor {
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
