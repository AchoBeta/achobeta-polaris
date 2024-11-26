package com.achobeta.types.support.util;

import com.achobeta.types.support.id.SnowflakeIdWorker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 严豪哲
 * @Description: Token工具类
 * @Date: 2024/11/11 18:37
 * @Version: 1.0
 */
public class TokenUtil {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "reflash_token";
    public static final String[] REFRESH_TOKEN_TYPE = {"reflash_token_12","reflash_token_30"};

    public static final String USER_ID = "userId";
    public static final String PHONE = "phone";
    public static final String DEVICE_ID = "deviceId";
    public static final String IP = "ip";
    public static final String TYPE = "type";
    public static final String IS_AUTO_LOGIN = "isAutoLogin";
    public static final String MAC = "mac";
    public static final String ID = "id";

    /**
     * 生成AccessToken
     * @param userId 用户的业务id
     * @param phone 用户的手机号
     * @param deviceId 用户的设备id
     * @param ip    用户的ip地址
     * @return 返回生成的AccessToken
     */
    public static String getAccessToken(Long userId, String phone, String deviceId, String ip, String mac) {
        //存储数据
        Map<String,Object> claims = new HashMap<>();
        claims.put(USER_ID,userId);
        claims.put(PHONE,phone);
        claims.put(DEVICE_ID,deviceId);
        claims.put(IP,ip);
        claims.put(TYPE,ACCESS_TOKEN);
        claims.put(ID, SnowflakeIdWorker.nextId());
        claims.put(MAC,mac);

        // 生成JWT令牌
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS384, ACCESS_TOKEN)
                .setClaims(claims)
                .compact();

        return jwt;
    }

    /**
     * 用于生成RefreshToken
     * @param userId 用户的业务id
     * @param phone 用户的手机号
     * @param deviceId 用户的设备id
     * @param ip    用户的ip地址
     * @param autoLogin 是否为自动登录
     * @return 返回生成的RefreshToken
     *
     */
    public static String getRefreshToken(Long userId, String phone, String deviceId, String ip, Boolean autoLogin, String mac) {
        //存储数据
        Map<String,Object> claims = new HashMap<>();
        claims.put(USER_ID,userId);
        claims.put(PHONE,phone);
        claims.put(DEVICE_ID,deviceId);
        claims.put(IP,ip);
        claims.put(TYPE,REFRESH_TOKEN_TYPE[autoLogin?1:0]);
        claims.put(ID, SnowflakeIdWorker.nextId());
        claims.put(MAC,mac);

        // 生成JWT令牌
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS384, REFRESH_TOKEN)
                .setClaims(claims)
                .compact();

        return jwt;
    }

    /**
     * 校验AccessToken是否合法
     * @param token 传入的AccessToken
     * @return 返回校验结果
     */
    public static boolean checkAccessToken(String token) {
        // 验证AccessToken
        try {
            Jwts.parser()
                    .setSigningKey(ACCESS_TOKEN)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 校验ReflashToken是否合法
     * @param token 传入的ReflashToken
     * @param autoLogin 是否为自动登录
     * @return 返回校验结果
     */

    public static boolean checkReflashToken(String token,Boolean autoLogin) {
        // 验证ReflashToken
        try {
            Jwts.parser()
                 .setSigningKey(REFRESH_TOKEN)
                 .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 从ReflashToken中获取type
     * @param token 传入的ReflashToken
     * @return 返回type
     */
    public static String getTypeByReflashToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(REFRESH_TOKEN)
                    .parseClaimsJws(token)
                        .getBody()
                        .get(TYPE)
                        .toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static Claims getClaimsByReflashToken(String token) {
        try {
            return Jwts.parser()
                  .setSigningKey(REFRESH_TOKEN)
                  .parseClaimsJws(token)
                      .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
