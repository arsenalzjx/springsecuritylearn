package com.zjx.security.core.properties;

/**
 * @author: zjx
 * @date: 2019/3/22 15:16
 */
public class OAuth2Properties {

    private OAuth2ClientProperties[] clients = {};

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }
}
