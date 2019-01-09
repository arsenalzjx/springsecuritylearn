package com.zjx.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 自定义配置SpringSocialConfigurer
 * @author: zjx
 * @date: 2019/1/7 10:43
 */
public class ZjxSpringSocialConfigurer extends SpringSocialConfigurer {

    /**
     * 过滤器拦截处理的回调url
     */
    private String filterProcessesUrl;

    public ZjxSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     * 在加入过滤器链前,自定义拦截url
     * @author: zjx
     * @date 10:51 2019/1/7
     * @param object 要增加到过滤器链中的过滤器
     * @return T
     **/
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return super.postProcess(object);
    }
}
