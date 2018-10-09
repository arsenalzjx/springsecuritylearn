package com.zjx.security.browser;

import com.zjx.security.browser.support.SimpleResponse;
import com.zjx.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>@ClassName: BrowserSecurityController </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/8 8:41</p>
 */
@RestController
public class BrowserSecurityController {


    private Logger logger = LoggerFactory.getLogger(getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @Author: zjx
     * @Description: 当需要身份认证时跳转到这里
     * @Date 8:44 2018/10/8
     * @Param [request, response]
     * @return java.lang.String
     **/
    @RequestMapping("/authentication/require")
    @ResponseStatus(code= HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //缓存当前请求
        SavedRequest savedRequest = requestCache.getRequest(request,response);

        if(savedRequest!=null){
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的url请求是:"+targetUrl);
            //判断如果当前请求是否为html请求
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                //如果是,则重定向到html页面,从配置文件中取出自定义配置的登录页面,或是默认配置的登录页面
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }
        }
        //如果不是html请求返回json字符串
        return new SimpleResponse("访问的服务需要身份认证,请引导用户到登录页");
    }
}
