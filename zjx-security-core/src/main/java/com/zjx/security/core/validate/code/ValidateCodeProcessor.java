package com.zjx.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * <p>@InterfaceName: ValidateCodeProcessor </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/15 10:01</p>
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * @Author: zjx
     * @Description: 创建校验码
     * @Date 10:03 2018/10/15
     * @Param [request]
     * @return void
     **/
    void create(ServletWebRequest request) throws Exception;
}
