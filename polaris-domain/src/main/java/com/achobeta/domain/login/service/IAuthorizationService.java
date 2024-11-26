package com.achobeta.domain.login.service;

import com.achobeta.domain.login.model.valobj.LoginVO;

/**
 * @Author: 严豪哲
 * @Description: 登录服务接口
 * @Date: 2024/11/9 21:04
 * @Version: 1.0
 */

public interface IAuthorizationService {

    /**
     * 登录
     * @param phone 用户手机号
     * @param code 验证码
     * @param ip    ip地址
     * @param isAutoLogin 是否自动登录
     * @return
     */
    LoginVO login(String phone, String code, String ip, Boolean isAutoLogin, String deviceName, String mac);
}
