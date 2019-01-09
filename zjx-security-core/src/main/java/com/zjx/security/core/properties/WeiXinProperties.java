package com.zjx.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * 微信属性配置类
 * @author: zjx
 * @date: 2019/1/9 19:29
 */
public class WeiXinProperties extends SocialProperties {
    private String providerId ="weixin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}