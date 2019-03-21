package com.zjx.security.app;

import com.zjx.security.core.social.ZjxSpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: zjx
 * @date: 2019/3/20 14:48
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    /**
     * 该操作为发生在组件加载到容器后,初始化前,不能反回null
     * @author: zjx
     * @date 14:55 2019/3/20
     * @param bean
     * @param beanName
     * @return java.lang.Object
     **/
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 该操作为发生在组件加载到容器后,初始化后,不能反回null
     * @author: zjx
     * @date 14:57 2019/3/20
     * @param bean
     * @param beanName
     * @return java.lang.Object
     **/
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals("zjxSocialSecurityConfig", beanName)) {
            ZjxSpringSocialConfigurer configurer = (ZjxSpringSocialConfigurer) bean;
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }
}
