package com.zjx.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * <p>@ClassName: TimeFilter </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/9/27 10:59</p>
 */
//@Component
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("time filter start");
        long start = new Date().getTime();
        chain.doFilter(request,response);
        System.out.println("time filter 耗时:"+(new Date().getTime()-start));
        System.out.println("time filter finish");

    }

    @Override
    public void destroy() {
        System.out.println("time filter destroy");
    }
}
