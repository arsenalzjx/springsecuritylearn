package com.zjx.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjx.security.browser.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: zjx
 * @date: 2019/2/22 14:47
 */
public class ZjxLogoutSuccessHandler implements LogoutSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String signOutUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public ZjxLogoutSuccessHandler(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    /**
     * 退出登录成功时处理器
     * @author: zjx
     * @date 14:53 2019/2/22
     * @param request
     * @param response
     * @param authentication
     * @return void
     **/
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("退出成功");
        if (StringUtils.isBlank(signOutUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        } else {
            response.sendRedirect(signOutUrl);
        }
    }
}
