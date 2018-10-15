package com.zjx.security.core.validate.code;

import java.time.LocalDateTime;

/**
 * <p>@ClassName: ValidateCode </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/9 15:55</p>
 */
public class ValidateCode {
    private String code;

    private LocalDateTime expireTime;


    public ValidateCode(String code, int expireTimeIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTimeIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isExpride() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
