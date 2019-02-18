package com.zjx.security.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session过期策略
 * @author: zjx
 * @date: 2019/2/18 10:55
 */
public class ZjxInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    public ZjxInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    /**
     * 当检测到session过期会调用的方法
     * @author: zjx
     * @date 13:46 2019/2/18
     * @param request
     * @param response
     * @return void
     **/
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        onSessionInvalid(request, response);
    }
}
