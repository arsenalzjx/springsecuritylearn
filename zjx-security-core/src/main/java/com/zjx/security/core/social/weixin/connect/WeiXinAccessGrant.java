package com.zjx.security.core.social.weixin.connect;

import org.springframework.social.oauth2.AccessGrant;

/**
 * 微信的access_token信息与OAuth2协议不同,微信在获取access_token时会同时返回openId,
 * 并没有单独的通过accessToken换区openId的服务,所以在这里继承标准AcessGrant,
 * 添加openId字段,作为对微信access_token信息的封装
 * @author: zjx
 * @date: 2019/1/10 13:39
 */
public class WeiXinAccessGrant extends AccessGrant {

    private String openId;

    public WeiXinAccessGrant() {
        super("");
    }

    public WeiXinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
