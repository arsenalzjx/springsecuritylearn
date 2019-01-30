package com.zjx.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * 自定义ConnectionSignUp,当获取到第三方登录信息后自动生成一个用户进行绑定
 * @author: zjx
 * @date: 2019/1/9 9:13
 */
//@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    /**
     * 根据一个连接生成用户id
     * @author: zjx
     * @date 9:15 2019/1/9
     * @param connection 社交用户的一个连接信息
     * @return java.lang.String
     **/
    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户,并返回用户唯一标识
        return connection.getDisplayName();
    }
}
