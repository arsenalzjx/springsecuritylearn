package com.zjx.security.core.validate.code.impl;

import com.zjx.security.core.validate.code.ValidateCode;
import com.zjx.security.core.validate.code.ValidateCodeGenerator;
import com.zjx.security.core.validate.code.ValidateCodeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * <p>@ClassName: AbstractValidateCodeProcessor </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/15 10:05</p>
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {


    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * spring自带依赖查找功能,会查找容器中所有接口实现,并将实现的组件的名字放入集合中当做key的值
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request,validateCode);
        send(request,validateCode);
    }

    /**
     * @Author: zjx
     * @Description: 保存校验码
     * @Date 10:44 2018/10/15
     * @Param [request, validateCode]
     * @return void
     **/
    protected  void save(ServletWebRequest request, C validateCode){
        sessionStrategy.setAttribute(request,SESSION_KEY_PREFIX+getProcessorType(request),validateCode);
    }


    private C generate(ServletWebRequest request){
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type+"CodeGenerator");
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * @Author: zjx
     * @Description: 根据请求的url获取校验码的类型
     * @Date 10:28 2018/10/15
     * @Param [request]
     * @return java.lang.String
     **/
    protected String getProcessorType(ServletWebRequest request){
        return StringUtils.substringAfter(request.getRequest().getRequestURI(),"/code/");
    }

    /**
     * @Author: zjx
     * @Description: 发送校验码,由子类具体实现
     * @Date 10:37 2018/10/15
     * @Param [request, validateCode]
     * @return void
     **/
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;
}
