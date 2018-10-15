package com.zjx.security.core.validate.code.sms;

/**
 * <p>@InterfaceName: SmsCodeSender </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/15 9:15</p>
 */
public interface SmsCodeSender {
    void send(String mobile,String code);
}
