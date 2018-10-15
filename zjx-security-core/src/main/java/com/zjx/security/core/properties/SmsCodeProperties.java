package com.zjx.security.core.properties;

/**
 * <p>@ClassName: ImageCodeProperties </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/10 9:29</p>
 */
public class SmsCodeProperties {

    private Integer length = 6;
    
    private Integer expireIn = 60;
    
    private String url;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Integer expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
