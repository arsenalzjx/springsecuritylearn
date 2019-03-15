package com.zjx.security.app.social.openid;

import com.zjx.security.core.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: zjx
 * @date: 2019/3/7 14:03
 */
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID;
    private String providerIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDERID;
    private boolean postOnly = true;

    /**
     * 构造对象时创建请求匹配拦截
     * @author: zjx
     * @date 10:43 2019/3/15
     **/
    protected OpenIdAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException{
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported:"+request.getMethod());
        }
        String openId = obtainOpenId(request);
        String providerId = obtainProviderId(request);

        if (openId == null) {
            openId = "";
        }
        if (providerId == null) {
            providerId = "";
        }

        openId = openId.trim();
        providerId = providerId.trim();

        OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openId, providerId);

        setDetails(request, authRequest);
        //将信息交由AuthenticationManager校验
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取openId
     */
    protected String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(openIdParameter);
    }

    /**
     * 获取提供商id
     */
    protected String obtainProviderId(HttpServletRequest request) {
        return request.getParameter(providerIdParameter);
    }


    protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public final String getOpenIdParameter() {
        return openIdParameter;
    }

    public void setOpenIdParameter(String openIdParameter) {
        Assert.hasText(openIdParameter, "openId属性不能为null或空字符串");
        this.openIdParameter = openIdParameter;
    }

    public String getProviderIdParameter() {
        return providerIdParameter;
    }

    public void setProviderIdParameter(String providerIdParameter) {
        this.providerIdParameter = providerIdParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
