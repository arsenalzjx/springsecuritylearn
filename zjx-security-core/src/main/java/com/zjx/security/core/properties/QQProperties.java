package com.zjx.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * QQ社交登录自定义配置,此处继承的为SpringSocial默认提供的社交配置
 * @author: zjx
 * @date: 2018/12/1 17:52
 */
public class QQProperties extends SocialProperties {

    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
