package com.zjx.security.core.social;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 处理社交登录中处理绑定解绑请求,该链接由{@link org.springframework.social.connect.web.ConnectController} 发起
 * 在{@link com.zjx.security.core.social.weixin.config.WeiXinAutoConfiguration} 中配置映射,默认由本类进行处理
 * @author: zjx
 * @date: 2019/1/25 9:45
 */
public class ZjxConnectView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        if (model.get("connections") == null) {
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("success");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<h3>绑定成功</h3>");
        }
    }
}
