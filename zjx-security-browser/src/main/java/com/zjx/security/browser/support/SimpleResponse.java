package com.zjx.security.browser.support;

/**
 * @author: zjx
 * @date: 2018/10/8 9:53
 */
public class SimpleResponse {
    private Object content;

    public SimpleResponse() {
    }

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
