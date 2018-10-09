package com.zjx.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * <p>@ClassName: ValidateCodeException </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/9 17:10</p>
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -41807396359869822L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
