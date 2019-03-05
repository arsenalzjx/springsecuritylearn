package com.zjx.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author: zjx
 * @date: 2019/3/4 16:15
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码接口
     * @author: zjx
     * @date 16:16 2019/3/4
     * @param request
     * @param code
     * @param validateCodeType 验证码类型,eg:sms短信验证码,iamge图形验证码
     **/
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);

    /**
     * 获取验证码接口,从相应存储中拿到对应验证码
     * @author: zjx
     * @date 16:18 2019/3/4
     * @param request
     * @param validateCodeType
     * @return com.zjx.security.core.validate.code.ValidateCode
     **/
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * 移除存储中储存的验证码
     * @author: zjx
     * @date 16:18 2019/3/4
     * @param request
     * @param validateCodeType
     * @return void
     **/
    void remove(ServletWebRequest request, ValidateCodeType validateCodeType);
}
