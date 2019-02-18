package com.zjx.security.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 自定义发生并发登录时的策略操作
 * @author: zjx
 * @date: 2019/2/15 16:29
 */
public class ZjxExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public ZjxExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    /**
     * 当发生相同账号挤掉之前登录的事件时,可在此处做记录处理
     * @author: zjx
     * @date 14:52 2019/2/18
     * @param event
     * @return void
     **/
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    /**
     * 更改默认
     * @author: zjx
     * @date 14:54 2019/2/18
     * @return boolean
     **/
    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
