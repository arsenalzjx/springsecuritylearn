package com.zjx.security.core.social.weixin.connect;

import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author: zjx
 * @date: 2019/1/9 21:12
 */
public class WeiXinOAuth2Temolate extends OAuth2Template {
    public WeiXinOAuth2Temolate(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
    }
}