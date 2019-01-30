package com.zjx.security.core.social.weixin.config;

import com.zjx.security.core.properties.SecurityProperties;
import com.zjx.security.core.properties.WeiXinProperties;
import com.zjx.security.core.social.ZjxConnectView;
import com.zjx.security.core.social.weixin.connect.WeiXinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 根据是否配置weixin相关的appId决定是否启用微信社交登录配置类
 * @author: zjx
 * @date: 2019/1/16 16:16
 */
@Configuration
@ConditionalOnProperty(prefix = "zjx.security.social.weixin",name = "app-id")
public class WeiXinAutoConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 重写创建连接工厂实例,根据配置类中
     * @author: zjx
     * @date 16:27 2019/1/16
     * @return org.springframework.social.connect.ConnectionFactory<?>
     **/
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeiXinProperties weixin = securityProperties.getSocial().getWeixin();
        return new WeiXinConnectionFactory(weixin.getProviderId(),
                weixin.getAppId(),weixin.getAppSecret());
    }

    /**
     * 注册一个处理绑定请求connect/weiXinConnected和解绑请求connect/weixinConnect的Bean
     * @author: zjx
     * @date 13:56 2019/1/25
     * @return org.springframework.web.servlet.View
     **/
    @Bean({"connect/weixinConnected","connect/weixinConnect"})
    @ConditionalOnMissingBean(name = "weiXinConnectedView")
    public View weiXinConnectedView(){
        return new ZjxConnectView();
    }

}
