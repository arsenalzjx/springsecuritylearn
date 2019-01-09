package com.zjx.security.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 自定义扩展OAuth2Template
 * @author: zjx
 * @date: 2019/1/7 15:01
 */
public class QQOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 扩展原有restTemplate,为其添加返回类型为text/html的转换器
     * @author: zjx
     * @date 15:52 2019/1/7
     * @return org.springframework.web.client.RestTemplate
     **/
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    /**
     * 重写替换处理服务端返回accessToken相应信息封装,
     * 父类的处理为服务端返回为json格式的情况下封装为对象,
     * 但QQ返回的为参数加&形式字符串,需自定义处理封装.
     * QQ返回格式:
     * access_token=FE04************************CCE2
     * &expires_in=7776000&refresh_token=88E4************************BE14
     * @author: zjx
     * @date 16:05 2019/1/7
     * @param accessTokenUrl 获取accessToken的链接
     * @param parameters 携带的参数
     * @return org.springframework.social.oauth2.AccessGrant
     **/
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        //发起请求获得为String类型的结果
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("获取accessToken的响应为:"+responseStr);
        //根据&分割字符串
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        //截取accessToken
        String accessToken = StringUtils.substringAfterLast(items[0],"=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1],"="));
        String refreshToken = StringUtils.substringAfterLast(items[2],"=");

        return new AccessGrant(accessToken, null,refreshToken ,expiresIn );
    }
}
