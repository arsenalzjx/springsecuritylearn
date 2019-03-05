package com.zjx.security.core.validate.code.impl;

import com.zjx.security.core.validate.code.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 模板方法,将不同的验证方式统一抽象,根据方法传递的模板ValidateCode不同进行不同的验证流程
 * @Author: zjx
 * @Date: 2018/10/15 10:05
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {


    /**
     * spring自带依赖查找功能,会查找容器中所有接口实现,并将实现的组件的名字放入集合中当做key的值
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 注入验证码操作实现类
     */
    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request,validateCode);
        send(request,validateCode);
    }

    private C generate(ServletWebRequest request){
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }


    /**
     * 保存校验码
     * @author: zjx
     * @date 10:44 2018/10/15
     * @param request
     * @param validateCode
     * @return void
     **/
    protected  void save(ServletWebRequest request, C validateCode){
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateCodeType(request));
    }


    /**
     * 执行校验的主方法
     * @author: zjx
     * @date 8:51 2019/3/5
     * @param request
     * @return void
     **/
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType validateCodeType = getValidateCodeType(request);

        C codeInSession = (C) validateCodeRepository.get(request, validateCodeType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    validateCodeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(validateCodeType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(validateCodeType + "验证码不存在");
        }

        if (codeInSession.isExpried()) {
            validateCodeRepository.remove(request, validateCodeType);
            throw new ValidateCodeException(validateCodeType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(validateCodeType + "验证码不匹配");
        }

        validateCodeRepository.remove(request, validateCodeType);
    }

    /**
     * 根据请求的url获取校验码的类型
     * @author: zjx
     * @date 14:37 2018/10/17
     * @param request
     * @return com.zjx.security.core.validate.code.ValidateCodeType
     **/
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 发送校验码,由子类具体实现
     * @author: zjx
     * @date 10:37 2018/10/15
     * @param request
     * @param validateCode
     * @return void
     **/
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;
}
