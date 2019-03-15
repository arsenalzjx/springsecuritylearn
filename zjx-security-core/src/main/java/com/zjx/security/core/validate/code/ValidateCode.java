package com.zjx.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: zjx
 * @Date: 2018/10/9 15:55
 */
public class ValidateCode implements Serializable {
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

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
