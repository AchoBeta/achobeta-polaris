package com.achobeta.domain.login.adapter.port;

/**
 * @Author: 严豪哲
 * @Description: 发送短信的的外部接口定义
 * @Date: 2024/11/7 19:35
 * @Version: 1.0
 */

public interface IShortMessageService {

    /*
     * 发送短信
     * @param phone 手机号
     * @return 短信验证码
     */
    String sms(String phone);

}
