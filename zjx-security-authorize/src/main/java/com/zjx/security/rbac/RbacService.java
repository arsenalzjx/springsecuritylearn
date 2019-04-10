package com.zjx.security.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zjx
 * @date: 2019/4/10 8:49
 */
public interface RbacService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
