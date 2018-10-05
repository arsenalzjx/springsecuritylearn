package com.zjx.security.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <p>@ClassName: MyUserDetailsService </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/4 15:19</p>
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登录用户名:"+username);
        //根据用户名查找用户信息
        //模拟数据库中取出加密后的密码
        String password = passwordEncoder.encode("123456");
        logger.info("数据库密码是:"+password);
        //根据查找到的用户信息判断,然后返回相应UserDetails信息
        return new User(username
                ,password
                ,true                       //是否删除
                ,true             //账户是否过期
                ,true          //密码是否过期
                ,true            //没被锁定
                ,AuthorityUtils.commaSeparatedStringToAuthorityList("admin")//该工具将以逗号为拆分符转换成权限对象

        );
    }
}
