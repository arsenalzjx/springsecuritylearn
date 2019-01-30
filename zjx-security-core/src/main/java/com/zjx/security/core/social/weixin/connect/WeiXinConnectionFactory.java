package com.zjx.security.core.social.weixin.connect;

import com.zjx.security.core.social.weixin.api.WeiXin;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 微信连接工厂
 * @author: zjx
 * @date: 2019/1/16 11:07
 */
public class WeiXinConnectionFactory extends OAuth2ConnectionFactory<WeiXin> {

    /**
     * 构造方法
     * @author: zjx
     * @date 11:09 2019/1/16
     * @param providerId 第三方提供商标识
     * @param appId 微信开放平台中创建的应用Id
     * @param appSecret 微信开放平台中创建的应用密码
     **/
    public WeiXinConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new WeiXinServiceProvider(appId, appSecret), new WeiXinAdapter());
    }


    /**
     * 这是个钩子方法,用于获得openId,标准OAuth2协议返回的是null,但微信中openId
     * 和accessToken一起返回,此时可从对应的封装类中取出,用于完成后续操作
     * @author: zjx
     * @date 13:59 2019/1/16
     * @param accessGrant 封装着从用微信端返回的OAuth协议所需属性信息
     * @return java.lang.String
     **/
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if (accessGrant instanceof WeiXinAccessGrant) {
            return ((WeiXinAccessGrant)accessGrant).getOpenId();
        }
        return null;
    }


    /**
     * 重写创建连接,因为标准OAuth协议中openId是最后获取用户信息时返回的,所以 {@link ApiAdapter}
     * 只需单例就可以处理该信息的适配,但微信在获取accessToken时openId就已经返回了,所以此时需创建
     * 多例{@link WeiXinAdapter} 并为内部属性openId传入之前获取的值
     * @author: zjx
     * @date 14:52 2019/1/16
     * @param accessGrant 获取accessToken后返回的标准
     * @return org.springframework.social.connect.Connection<com.zjx.security.core.social.weixin.api.WeiXin>
     **/
    @Override
    public Connection<WeiXin> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant),
                accessGrant.getAccessToken(),accessGrant.getRefreshToken(),accessGrant.getExpireTime(),
                getOAuth2ServiceProvider(),getApiAdapter(extractProviderUserId(accessGrant)));
    }

    /**
     * 重写创建连接方法,因为标准OAuth协议中openId是最后获取用户信息时返回的,所以 {@link ApiAdapter}
     * 只需单例就可以处理该信息的适配,但微信在获取accessToken时openId就已经返回了,所以此时需创建
     * 多例{@link WeiXinAdapter} 并为内部属性openId传入之前获取的值
     * @author: zjx
     * @date 15:51 2019/1/16
     * @param data OAuth2协议标准属性的封装
     * @return org.springframework.social.connect.Connection<com.zjx.security.core.social.weixin.api.WeiXin>
     **/
    @Override
    public Connection<WeiXin> createConnection(ConnectionData data) {
        return new OAuth2Connection<>(data, getOAuth2ServiceProvider(),
                getApiAdapter(data.getProviderUserId()));
    }

    /**
     * 根据openId创建不同的ApiAdapter并返回
     * @author: zjx
     * @date 16:14 2019/1/16
     * @param providerUserId 微信平台中返回的openId
     * @return org.springframework.social.connect.ApiAdapter<com.zjx.security.core.social.weixin.api.WeiXin>
     **/
    private ApiAdapter<WeiXin> getApiAdapter(String providerUserId) {
        return new WeiXinAdapter(providerUserId);
    }

    /**
     * @author: zjx
     * @date 16:07 2019/1/16
     * @return org.springframework.social.oauth2.OAuth2ServiceProvider<com.zjx.security.core.social.weixin.api.WeiXin>
     **/
    private OAuth2ServiceProvider<WeiXin> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WeiXin>) getServiceProvider();
    }

}
