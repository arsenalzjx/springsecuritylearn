package com.zjx.security.core.social.qq.config;

import com.zjx.security.core.properties.QQProperties;
import com.zjx.security.core.properties.SecurityProperties;
import com.zjx.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * 社交自动配置类,
 * 当在配置文件中配置了zjx.security.social.qq.app-id时自动配置生效
 * @author: zjx
 * @date: 2018/12/1 17:57
 */
@Configuration
@ConditionalOnProperty(prefix = "zjx.security.social.qq",name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    /**
     * 注入属性配置文件
     */
    @Autowired
    SecurityProperties securityProperties;

    /**
     *
     * @author: zjx
     * @date 15:40 2019/1/4
     * @return org.springframework.social.connect.ConnectionFactory<?>
     **/
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqProperties.getProviderId(),qqProperties.getAppId(),qqProperties.getAppSecret() );
    }

}
