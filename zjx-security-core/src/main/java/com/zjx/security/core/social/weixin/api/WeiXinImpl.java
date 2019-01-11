package com.zjx.security.core.social.weixin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @author: zjx
 * @date: 2019/1/9 19:34
 */
public class WeiXinImpl extends AbstractOAuth2ApiBinding implements WeiXin {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取微信用户信息api地址
     */
    private static final String URL_GET_USER_INFO= "https://api.weixin.qq.com/sns/userinfo?openid=%s";

    /**
     * 重写构造方法,更改accessToken携带策略,默认为请求头携带,更改为请求url参数携带
     * @author: zjx
     * @date 20:02 2019/1/9
     * @param accessToken 微信所给令牌
     **/
    public WeiXinImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    /**
     * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，而微信返回的是UTF-8的，所以覆盖了原来的方法。
     * @author: zjx
     * @date 20:52 2019/1/9
     * @return java.util.List<org.springframework.http.converter.HttpMessageConverter<?>>
     **/
    /*@Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(1);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }*/

    @Override
    public WeiXinUserInfo getUserInfo(String openId) {
        //将openId传入api参数对应位置
        String url = String.format(URL_GET_USER_INFO,openId);
        String result = getRestTemplate().getForObject(url, String.class);
        logger.info("从微信获取的用户信息为:"+result);
        //当含有微信错误码时,返回null
        if(StringUtils.contains(result,"errcode")){
            return null;
        }
        WeiXinUserInfo weiXinUserInfo = null;
        try {
            weiXinUserInfo = objectMapper.readValue(result, WeiXinUserInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weiXinUserInfo;
    }
}