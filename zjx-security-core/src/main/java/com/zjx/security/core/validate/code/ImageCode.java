package com.zjx.security.core.validate.code;

import java.awt.image.BufferedImage;

/**
 * <p>@ClassName: ImageCode </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/9 15:55</p>
 */
public class ImageCode extends ValidateCode {
    private BufferedImage image;


    public ImageCode(BufferedImage image, String code, int expireTimeIn) {
        super(code,expireTimeIn);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
