package com.zjx.security.core.social.weixin.connect;

import com.zjx.security.core.social.weixin.api.WeiXin;
import com.zjx.security.core.social.weixin.api.WeiXinImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 负责处理微信服务端的交互类
 * @author: zjx
 * @date: 2019/1/11 15:25
 */
public class WeiXinServiceProvider extends AbstractOAuth2ServiceProvider<WeiXin> {

    /**
     * 向微信获取授权码接口url
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";

    /**
     * 向微信获取令牌接口url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public WeiXinServiceProvider(String appId,String appSecret) {
        super(new WeiXinOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
    }

    @Override
    public WeiXin getApi(String accessToken) {
        return new WeiXinImpl(accessToken);
    }
}
