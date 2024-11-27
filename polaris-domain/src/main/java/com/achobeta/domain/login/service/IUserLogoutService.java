package com.achobeta.domain.login.service;

import com.achobeta.domain.login.model.valobj.LogoutVO;

/**
 * @Author: 严豪哲
 * @Description:
 * @Date: 2024/11/19 20:59
 * @Version: 1.0
 */
public interface IUserLogoutService {

    LogoutVO logout(String accessToken, String deviceId);
}
