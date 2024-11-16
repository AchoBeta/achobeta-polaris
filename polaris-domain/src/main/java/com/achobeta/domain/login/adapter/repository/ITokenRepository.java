package com.achobeta.domain.login.adapter.repository;

import com.achobeta.domain.login.model.valobj.TokenVO;

/**
 * @Author: 严豪哲
 * @Description: 令牌仓储层接口定义
 * @Date: 2024/11/12 19:15
 * @Version: 1.0
 */

public interface ITokenRepository {

    /**
     * 存储Accesstoken
     * @param token token的值
     * @param userId 用户id
     * @param phone 手机号
     * @param devicId 设备id
     * @param ip ip地址
     */
    void storeAccessToken(String token, String userId, String phone, String devicId, String ip);

    /**
     * 存储Reflashtoken
     * @param token token的值
     * @param userId 用户id
     * @param phone 手机号
     * @param devicId 设备id
     * @param ip ip地址
     * @param isAutoLogin 是否自动登录
     */
    void storeReflashToken(String token, String userId, String phone, String devicId, String ip, Boolean isAutoLogin);

    /**
     * 根据accesstoken删除accesstoken
     * @param token token的值
     */
    void deleteAccessToken(String token);

    /**
     * 根据reflashtoken删除reflashtoken
     * @param token token的值
     */
    void deleteReflashToken(String token);

    /**
     * 根据设备id删除token
     * @param deviceId 设备id
     */
    void deleteTokenByDeviceId(String deviceId);

    /**
     * 检查token是否存在
     * @param token token的值
     */
    Boolean checkToken(String token);

    /**
     * 重置Reflashtoken的过期时间
     * @param token token的值
     */
    void resetReflashTokenExpired(String token);
}
