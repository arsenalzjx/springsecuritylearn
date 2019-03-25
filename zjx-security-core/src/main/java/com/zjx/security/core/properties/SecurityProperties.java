package com.zjx.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: zjx
 * @Date: 2018/10/8 10:08
 */
@ConfigurationProperties(prefix = "zjx.security")
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private SocialProperties social = new SocialProperties();

    private OAuth2Properties oAuth2 = new OAuth2Properties();

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public OAuth2Properties getoAuth2() {
        return oAuth2;
    }

    public void setoAuth2(OAuth2Properties oAuth2) {
        this.oAuth2 = oAuth2;
    }
}
