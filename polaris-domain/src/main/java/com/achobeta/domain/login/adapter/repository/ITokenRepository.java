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
     * @param deviceId 设备id
     * @param ip ip地址
     */
    void storeAccessToken(String token, String userId, String phone, String deviceId, String ip);

    /**
     * 存储Reflashtoken
     * @param token token的值
     * @param userId 用户id
     * @param phone 手机号
     * @param deviceId 设备id
     * @param ip ip地址
     * @param isAutoLogin 是否自动登录
     */
    void storeReflashToken(String token, String userId, String phone, String deviceId, String ip, Boolean isAutoLogin);

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
     * 根据设备id删除全部token
     * 强制下线
     * @param deviceId 设备id
     */
    void deleteTokenByDeviceId(String deviceId);

    /**
     * 检查token是否存在
     * @param token token的值
     */
    Boolean checkToken(String token);

    /**
     * 检查accesstoken
     * 针对access_token的,能返回该token状态
     * @param token token的值
     * @return 0:已过期 1:未过期 -1:已删除
     */
    int checkAccessToken(String token);

    /**
     * 重置Reflashtoken的过期时间
     * @param token token的值
     */
    void resetReflashTokenExpired(String token);

    /**
     * 根据refrtoken获取token信息
     * @param token token的值
     */
    TokenVO getReflashTokenInfo(String token);

    /**
     * 根据accesstoken获取token信息
     * @param token
     * @return
     */
    TokenVO getAccessTokenInfo(String token);

    /**
     * 获取accesstoken的过期时间
     * @param token
     * @return
     */
    Long getAccessTokenExpired(String token);

}
