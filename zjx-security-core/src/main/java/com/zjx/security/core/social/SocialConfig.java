package com.zjx.security.core.social;

import com.zjx.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 社交配置注册到容器中
 * @author: zjx
 * @date: 2018/12/1 17:19
 */
@Order(10)
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    /**
     * 注入数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 注入属性配置文件
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 通过社交连接生成用户的工具类,此功能交由其他应用端自定义
     * 处理,所以此处不一定会有该工具类注册到容器
     */
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    /**
     * 注册一个和social相关数据表交互的连接服务到容器中
     * @author: zjx
     * @date 16:14 2019/1/4
     * @param connectionFactoryLocator 用于获取相应的connectionFactory,由springboot自动装配
     * @return org.springframework.social.connect.UsersConnectionRepository
     **/
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //JdbcUsersConnectionRepository源代码位置有相应的初始化sql语句,用于建表
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                //数据行
                connectionFactoryLocator,
                //对信息进行加密的加密方式
                Encryptors.noOpText());
        //设置表前缀
        repository.setTablePrefix("zjx_");
        if (connectionSignUp!=null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 注册一个自定义SpringSocial配置到容器中
     * @author: zjx
     * @date 17:21 2019/1/4
     * @return org.springframework.social.security.SpringSocialConfigurer
     **/
    @Bean
    public SpringSocialConfigurer zjxSocialSecurityConfig(){
        //将filterProcessesUrl从属性配置文件中取出,用于自定义构造SpringSocialConfigurer到容器中
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        ZjxSpringSocialConfigurer configurer = new ZjxSpringSocialConfigurer(filterProcessesUrl);
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        return configurer;
    }

    /**
     * 注册第三方服务工具组件(用于从session中获取存储的第三方用户信息)
     * @author: zjx
     * @date 14:26 2019/1/8
     * @param connectionFactoryLocator 用于获取相应的connectionFactory,由springboot自动装配
     * @return org.springframework.social.connect.web.ProviderSignInUtils
     **/
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)){};
    }
}
