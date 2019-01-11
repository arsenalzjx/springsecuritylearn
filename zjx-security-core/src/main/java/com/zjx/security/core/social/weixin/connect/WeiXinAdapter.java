package com.zjx.security.core.social.weixin.connect;

import com.zjx.security.core.social.weixin.api.WeiXin;
import com.zjx.security.core.social.weixin.api.WeiXinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 微信数据适配SpringSocial标准数据的适配器
 * @author: zjx
 * @date: 2019/1/11 14:06
 */
public class WeiXinAdapter implements ApiAdapter<WeiXin> {

    private String openId;

    public WeiXinAdapter() {
    }

    public WeiXinAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(WeiXin api) {
        return true;
    }

    /**
     * 调用接口完成获取微信用户信息功能,之后再将用户信息封装到SpringSocial
     * 通用封装类中完成数据适配
     * @author: zjx
     * @date 14:16 2019/1/11
     * @param api 微信api
     * @param values SpringSocial标准数据封装类
     * @return void
     **/
    @Override
    public void setConnectionValues(WeiXin api, ConnectionValues values) {
        WeiXinUserInfo weiXinUserInfo = api.getUserInfo(openId);
        values.setProviderUserId(weiXinUserInfo.getOpenid());
        values.setDisplayName(weiXinUserInfo.getNickname());
        values.setImageUrl(weiXinUserInfo.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(WeiXin api) {
        return null;
    }

    @Override
    public void updateStatus(WeiXin api, String message) {
        //do nothing
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
