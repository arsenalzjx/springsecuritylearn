package com.zjx.security.core.validate.code;

import com.zjx.security.core.properties.SecurityProperties;
import com.zjx.security.core.validate.code.image.ImageCodeGenerator;
import com.zjx.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.zjx.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>@ClassName: ValidateCodeBeanConfig </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/10 15:09</p>
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        SmsCodeSender smsCodeSender = new DefaultSmsCodeSender();
        return smsCodeSender;
    }
}
