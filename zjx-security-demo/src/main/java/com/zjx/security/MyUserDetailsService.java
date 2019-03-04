package com.zjx.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 登录服务
 * @author: zjx
 * @date: 2018/10/4 15:19
 */
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 表单登录时使用,根据username
     * @author: zjx
     * @date 16:41 2019/1/4
     * @param username 用户填写表单时的用户名
     * @return org.springframework.security.core.userdetails.UserDetails
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("表单登录用户名:"+username);
        return buildUser(username);
    }

    /**
     * 根据用户id进行表单登录
     * @author: zjx
     * @date 16:54 2019/1/4
     * @param userId 用户唯一id,对应社交登录表中的userId
     * @return org.springframework.social.security.SocialUserDetails
     **/
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("社交登录用户ID:"+userId);
        return buildUser(userId);
    }

    /**
     * 登录验证方法抽取出的公共方法,此处返回为SocialUserDetails而非UserDetails
     * 是为了可查询相关社交登录信息,如果返回的为Userdetails则因为内部没有查询
     * userId方法而无法获得用户是否绑定了相应社交账号
     * @author: zjx
     * @date 10:06 2019/1/25
     * @param userId
     * @return org.springframework.social.security.SocialUserDetails
     **/
    private SocialUserDetails buildUser(String userId) {
        /*根据用户名查找用户信息
        模拟数据库中取出加密后的密码*/
        String password = passwordEncoder.encode("123456");
        logger.info("数据库密码是:"+password);
        //根据查找到的用户信息判断,然后返回相应UserDetails信息
        return new SocialUser(userId
                ,password
                //是否删除
                ,true
                //账户是否过期
                ,true
                //密码是否过期
                ,true
                //没被锁定
                ,true
                //该工具将以逗号为拆分符转换成权限对象
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER")
        );
    }
}
