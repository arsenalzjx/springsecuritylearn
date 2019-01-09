package com.zjx.security.core.social.qq.connect;

import com.zjx.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * QQ连接工厂,用于产生连接,实现连接多例
 * @author: zjx
 * @date: 2018/10/22 12:53
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
    /**
     * 通过自定义构造函数构造连接工厂
     * @author: zjx
     * @date 15:43 2019/1/4
     * @param providerId 社交提供商的唯一标识
     * @param appId 社交服务端注册的应用id
     * @param appSecret 社交服务端注册的应用密码
     **/
    public QQConnectionFactory(String providerId,String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
