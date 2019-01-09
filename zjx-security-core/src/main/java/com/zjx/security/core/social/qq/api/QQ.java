package com.zjx.security.core.social.qq.api;

/**
 * 从QQ获取数据的接口
 * @author: zjx
 * @date: 2018/10/22 9:25
 */
public interface QQ {

    /**
     * 从QQ互联中获取QQ用户信息,并封装成QQUserInfo返回
     * @author: zjx
     * @date 15:32 2019/1/4
     * @return com.zjx.security.core.social.qq.api.QQUserInfo
     **/
    QQUserInfo getUserInfo();
}
