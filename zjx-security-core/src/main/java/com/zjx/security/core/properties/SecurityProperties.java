package com.zjx.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>@ClassName: SecurityProperties </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/8 10:08</p>
 */
@ConfigurationProperties(prefix = "zjx.security")
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
