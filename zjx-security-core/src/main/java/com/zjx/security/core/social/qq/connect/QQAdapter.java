package com.zjx.security.core.social.qq.connect;

import com.zjx.security.core.social.qq.api.QQ;
import com.zjx.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * QQ信息适配器,将从QQ获取到的信息适配到SpringSocial里
 * @author: zjx
 * @date: 2018/10/22 12:45
 */
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     * 测试连接是否正常
     * @author: zjx
     * @date 11:14 2019/1/3
     * @param api qq接口相关的实现类
     * @return boolean
     **/
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
     * 适配方法,将从qq获取到的值设置到springSocial中
     * @author: zjx
     * @date 15:25 2019/1/4
     * @param api qq接口相关实现类
     * @param values SpringSocial默认提供的通用信息封装
     * @return void
     **/
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        //设置显示名称
        values.setDisplayName(userInfo.getNickname());
        //设置头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        //设置个人主页,因qq中没有个人主页概念,若是微博等则可设置该值
        values.setProfileUrl(null);
        //服务商用户id
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    /**
     * 发消息更新状态(类似微博),但qq没有个人主页等概念,所以不做处理
     * @author: zjx
     * @date 14:36 2019/1/4
     * @param api qq接口相关实现类
     * @param message 更新的消息
     * @return void
     **/
    @Override
    public void updateStatus(QQ api, String message) {
        //do nothing
    }

}
