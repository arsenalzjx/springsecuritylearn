package com.zjx.security.core.properties;

/**
 * 关于BS的配置
 * @author: zjx
 * @date: 2018/10/8 10:08
 */
public class BrowserProperties {

    /**
     * 注册页面
     */
    private String signUpUrl = "/zjx-signUp.html";

    /**
     * 登录页面
     */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private LoginType loginType = LoginType.JSON;

    /**
     * '记住我'功能的有效时间，默认1小时
     */
    private int rememberMeSeconds = 3600;

    /**
     * session管理配置项
     */
    private SessionProperties session = new SessionProperties();

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }
}
