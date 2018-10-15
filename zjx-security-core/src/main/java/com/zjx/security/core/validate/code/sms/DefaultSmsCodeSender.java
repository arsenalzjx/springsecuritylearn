package com.zjx.security.core.validate.code.sms;

/**
 * <p>@ClassName: DefaultSmsCodeSender </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/15 9:17</p>
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机"+mobile+"发送短信验证码:"+code);
    }
}
