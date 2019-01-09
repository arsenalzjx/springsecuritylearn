package com.zjx.security.core.social.qq.connect;

import com.zjx.security.core.social.qq.api.QQ;
import com.zjx.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * QQ服务提供类,用于完成获取accessToken及自定义API接收数据
 * @author: zjx
 * @date: 2018/10/22 10:38
 */
    public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    /**
     * qq互联中创建应用的应用Id
     */
    private String appId;

    /**
     * 导向认真服务器地址,该地址可获取授权码
     */
    public static final String URL_AUTHORIZW = "https://graph.qq.com/oauth2.0/authorize";

    /**
     * 导向获取accessToKen地址,该地址用于获取accessToken
     */
    public static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    /**
     * 编写子类构造方法,在构造对象时调用父类构造方法,传入相应数据,
     * 完成OAuth2中导向认证服务器获取授权码及导向认证服务器获取accessToken
     * @author: zjx
     * @date 15:43 2019/1/4
     * @param appId 社交服务端注册的应用id
     * @param appSecret 社交服务端注册的应用密码
     **/
    public QQServiceProvider(String appId,String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZW, URL_ACCESS_TOKEN));
        this.appId = appId;
    }


    /**
     * 根据accessToken获取用户信息
     * @author: zjx
     * @date 15:44 2019/1/4
     * @param accessToken 社交服务端发给的令牌
     * @return com.zjx.security.core.social.qq.api.QQ
     **/
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }
}
