package com.zjx.security.app;

import com.zjx.security.app.social.AppSignUpUtils;
import com.zjx.security.core.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zjx
 * @date: 2019/3/20 14:58
 */
@RestController
public class AppSecurityController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSignUpUtils appSignUpUtils;

    @GetMapping("/social/signUp")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request){
        SocialUserInfo userinfo = new SocialUserInfo();
        //获取到用户相对应的第三方平台用户信息
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        //从临时数据中补全信息
        userinfo.setProviderId(connection.getKey().getProviderId());
        userinfo.setProviderUserId(connection.getKey().getProviderUserId());
        userinfo.setNickname(connection.getDisplayName());
        userinfo.setHeadimg(connection.getImageUrl());
        appSignUpUtils.saveConnectionData(new ServletWebRequest(request),connection.createData());

        return userinfo;
    }
}
