package com.zjx.security.browser.support;

/**
 * <p>@ClassName: SimpleResponse </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/8 9:53</p>
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
