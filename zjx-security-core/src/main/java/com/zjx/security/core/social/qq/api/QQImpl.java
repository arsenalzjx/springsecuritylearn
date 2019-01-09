package com.zjx.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * 从QQ获取信息实现类,因accessToken和openId会有不同,使用多例模式
 * @author: zjx
 * @date: 2018/10/22 9:27
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    /**
     * 根据某用户Token查询qq中该用户的openID
     */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 根据该用户的openId和第三方应用appId(oauth_consumer_key),
     * 再加上用户Token获取用户信息,AbstractOAuth2ApiBinding父类中设置策略,
     * 可自动将令牌参追加到url中,所以不需在url中体现
     */
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    /**
     * qq互联中创建的应用Id
     */
    private String appId;

    /**
     * qq用户的唯一标识
     */
    private String openId;

    /**
     * 用于处理字符串转换为对象属性的工具类
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 重写子类构造方法,自定义构造时创建的restTemplate策略,
     * 完成对accessToken及appid的传入,并获取openid
     * @author: zjx
     * @date 15:27 2019/1/4
     * @param accessToken 社交服务端发放的令牌
     * @param appId 社交服务端记录的第三方应用id
     **/
    public QQImpl(String accessToken,String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID,accessToken);
        String result = getRestTemplate().getForObject(url,String.class);
        logger.info("获取到的openid信息为");
        System.out.println(result);
        this.openId = StringUtils.substringBetween(result,"\"openid\":\"","\"}");
    }

    /**
     * 用已获得的openId及accessToken获取用户信息
     * @author: zjx
     * @date 15:39 2019/1/4
     * @return com.zjx.security.core.social.qq.api.QQUserInfo
     **/
    @Override
    public QQUserInfo getUserInfo(){
        String url = String.format(URL_GET_USERINFO,appId, openId);
        String result = getRestTemplate().getForObject(url,String.class);
        logger.info("获取到的用户信息为:"+result);
        QQUserInfo qqUserInfo = null;
        try {
            qqUserInfo = objectMapper.readValue(result,QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
            return qqUserInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败",e);
        }
    }
}
