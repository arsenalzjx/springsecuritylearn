package com.zjx.security.core.properties;

/**
 * <p>@ClassName: ImageCodeProperties </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/10 9:29</p>
 */
public class ImageCodeProperties extends SmsCodeProperties {
    private Integer width = 67;

    private Integer height = 23;

    public ImageCodeProperties() {
        setLength(4);
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

}
