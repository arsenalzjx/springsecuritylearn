package com.zjx.security.app.social;

import com.zjx.security.app.AppSecretException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @author: zjx
 * @date: 2019/3/20 9:28
 */
@Component
public class AppSignUpUtils {

    /**
     * 操作redis数据库
     */
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    /**
     * 操作数据库
     **/
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    /**
     * 连接工厂定位器,可根据某些条件返回对应的连接工厂
     */
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;
    /**
     * 将连接信息存入redis中
     * @author: zjx
     * @date 10:06 2019/3/20
     * @param request
     * @param connectionData
     * @return void
     **/
    public void  saveConnectionData(WebRequest request, ConnectionData connectionData) {
        redisTemplate.opsForValue().set(getKey(request), connectionData, 10, TimeUnit.MINUTES);

    }

    /**
     * 注册和绑定时调用
     * @author: zjx
     * @date 11:05 2019/3/20
     * @param request
     * @param userId 注册新用户或者选择旧用户绑定时都会有相应的userId参数
     * @return void
     **/
    public void doPostSignUp(WebRequest request,String userId) {
        String key = getKey(request);
        //验证在redis中查是否有社交账号信息
        if (!redisTemplate.hasKey(key)) {
            throw new AppSecretException("无法找到缓存的用户社交账号信息");
        }
        //从redis中取出连接数据
        ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);
        //获取到对应的连接
        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
        //将新注册用户的userId或者绑定旧用户的userId和社交信息做绑定
        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);
        //信息绑定完成后移除缓存中相应数据
        redisTemplate.delete(key);
    }


    /**
     * 校验并生成redis中的key
     * @author: zjx
     * @date 10:06 2019/3/20
     * @param request
     * @return java.lang.String
     **/
    private String getKey(WebRequest request) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new AppSecretException("设备Id参数不能为空");
        }

        return "zjx:security:social.connect."+deviceId;
    }
}
