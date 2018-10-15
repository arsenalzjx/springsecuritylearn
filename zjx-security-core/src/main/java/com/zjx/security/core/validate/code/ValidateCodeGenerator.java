package com.zjx.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * <p>@InterfaceName: ValidateCodeGenerator </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/10 15:04</p>
 */
public interface ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);
}
