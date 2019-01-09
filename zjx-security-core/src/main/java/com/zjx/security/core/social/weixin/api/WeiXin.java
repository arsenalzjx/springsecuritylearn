package com.zjx.security.core.social.weixin.api;

/**
 * 社交登陆微信接口
 * @author: zjx
 * @date: 2019/1/9 19:28
 */
public interface WeiXin {
    /**
     * 根据openId获取用户信息
     * @author: zjx
     * @date 21:10 2019/1/9
     * @param openId 微信平台用户唯一标识
     * @return com.zjx.security.core.social.weixin.api.WeiXinUserInfo
     **/
    WeiXinUserInfo getUserInfo(String openId);
}