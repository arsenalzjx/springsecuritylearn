package com.zjx.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>@ClassName: ValidateCodeProperties </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/10 9:35</p>
 */
@ConfigurationProperties()
public class ValidateCodeProperties {
    private ImageCodeProperties image = new ImageCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
