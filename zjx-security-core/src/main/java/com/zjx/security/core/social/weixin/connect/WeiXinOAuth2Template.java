package com.zjx.security.core.social.weixin.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 国内厂商实现的OAuth2每个都不同,SpringSocial默认提供的OAuth2Template适应不了只能针对每个厂商微调
 * 微信:参数名称(appid,secret,code,grant_type,)
 * 标准OAuth2协议:参数名称(clientId,clientSecret,code,grant_type)
 * @author: zjx
 * @date: 2019/1/9 21:12
 */
public class WeiXinOAuth2Template extends OAuth2Template {

    /**
     * 微信中第三方应用Id
     */
    private String clientId;

    /**
     * 微信中第三方应用密码
     */
    private String clientSecret;

    /**
     * 微信
     */
    private String accessTokenUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 子类构造方法,父类属性为私有,重写的方法无法使用,此时需给子类的属性赋值
     * @author: zjx
     * @date 16:25 2019/1/10
     * @param clientId 微信平台对应的appId
     * @param clientSecret 微信平台对应的secret
     * @param authorizeUrl 微信平台获取授权码的url
     * @param accessTokenUrl 微信平台获取令牌的url
     **/
    public WeiXinOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    /**
     * 获取微信令牌方法,拼装符合微信获取令牌规范接口url,并发送请求获取令牌
     * @author: zjx
     * @date 9:08 2019/1/11
     * @param authorizationCode 微信所返回的授权码
     * @param redirectUri 跳转的链接
     * @param additionalParameters 封装的参数(因微信不与OAuth2协议完全匹配,所以此处无用)
     * @return org.springframework.social.oauth2.AccessGrant
     **/
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);
        accessTokenRequestUrl.append("?appid="+clientId);
        accessTokenRequestUrl.append("&secret+"+clientSecret);
        accessTokenRequestUrl.append("&code="+authorizationCode);
        accessTokenRequestUrl.append("grant_type=authorization_code");
        accessTokenRequestUrl.append("&redirect_uri="+redirectUri);
        return getAccessToken(accessTokenRequestUrl);
    }


    /**
     * 刷新令牌方法,拼装符合微信刷新令牌规范的接口url,并发送请求获取令牌
     * @author: zjx
     * @date 11:19 2019/1/11
     * @param refreshToken 微信返回的刷新令牌口令
     * @param additionalParameters 封装的参数(因微信不与OAuth2协议完全匹配,所以此处无用)
     * @return org.springframework.social.oauth2.AccessGrant
     **/
    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
        StringBuilder refreshTokenUrl = new StringBuilder(REFRESH_TOKEN_URL);
        refreshTokenUrl.append("?appid="+clientId);
        refreshTokenUrl.append("&grant_type=refresh_token");
        refreshTokenUrl.append("&refresh_token="+refreshToken);
        return getAccessToken(refreshTokenUrl);
    }

    /**
     * 抽取获得令牌方法,因最终返回的响应中参数一致,所以只需
     * 传入不同的url即可实现获取令牌及刷新令牌的方法复用
     * @author: zjx
     * @date 9:19 2019/1/11
     * @param accessTokenRequestUrl 获取令牌的url
     * @return org.springframework.social.oauth2.AccessGrant
     **/
    private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl){
        logger.info("获取微信accessToken的请求url是:"+accessTokenRequestUrl);
        String response = getRestTemplate().getForObject(accessTokenRequestUrl.toString(), String.class);
        logger.info("微信响应accessToken的请求结果为:"+response);
        Map<String,Object> result = null;
        try {
            result = objectMapper.readValue(response,Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回错误码处理
        if (StringUtils.isNotBlank(MapUtils.getString(result, "errcode"))) {
            String errcode = MapUtils.getString(result, "errcode");
            String errmsg = MapUtils.getString(result, "errmsg");
            throw new RuntimeException("获取access token失败, errcode:"+errcode+", errmsg:"+errmsg);
        }
        WeiXinAccessGrant accessGrant = new WeiXinAccessGrant(
                MapUtils.getString(result, "access_token"),
                MapUtils.getString(result, "scope"),
                MapUtils.getString(result,"refresh_token" ),
                MapUtils.getLong(result, "expires_in"));
        accessGrant.setOpenId(MapUtils.getString(result,"openid"));
        return accessGrant;
    }

    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }

    /**
     * 构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     */
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid="+clientId+"&scope=snsapi_login";
        return url;
    }

    /**
     * 微信返回的contentType是html/text，添加相应的HttpMessageConverter来处理。
     */
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }



}