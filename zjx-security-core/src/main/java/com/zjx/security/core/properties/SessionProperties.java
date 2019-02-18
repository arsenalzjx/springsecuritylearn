package com.zjx.security.core.properties;

/**
 * Session相关的配置项
 * @author: zjx
 * @date: 2019/2/18 9:32
 */
public class SessionProperties {
    /**
     * 同一个用户在系统中的最大session数,默认是1
     */
    private Integer maximumSessions = 1;

    /**
     * 达到最大session时,是否组织新的登录请求,默认为false,不阻止,新的登录会让旧的登录失效
     */
    private Boolean maxSessionPreventsLogin = false;

    /**
     * session失效时跳转的地址
     */
    private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;

    public Integer getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(Integer maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public Boolean getMaxSessionPreventsLogin() {
        return maxSessionPreventsLogin;
    }

    public void setMaxSessionPreventsLogin(Boolean maxSessionPreventsLogin) {
        this.maxSessionPreventsLogin = maxSessionPreventsLogin;
    }

    public String getSessionInvalidUrl() {
        return sessionInvalidUrl;
    }

    public void setSessionInvalidUrl(String sessionInvalidUrl) {
        this.sessionInvalidUrl = sessionInvalidUrl;
    }
}
