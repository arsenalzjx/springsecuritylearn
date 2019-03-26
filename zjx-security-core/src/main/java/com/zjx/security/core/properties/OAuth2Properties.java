package com.zjx.security.core.properties;

/**
 * @author: zjx
 * @date: 2019/3/22 15:16
 */
public class OAuth2Properties {

    /**
     * jwt令牌签名秘钥
     */
    private String jwtSigningKey = "zjx";

    /**
     * 客户端配置
     */
    private OAuth2ClientProperties[] clients = {};

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }
}
