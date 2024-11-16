package com.achobeta.domain.login.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 严豪哲
 * @Description: token的值对象
 * @Date: 2024/11/11 22:00
 * @Version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenVO {

    /*
     * 登录用户的accessToken
     */
    private String accessToken;

    /*
     * 登录用户的refreshToken
     */
    private String refreshToken;

    /*
     * 登录用户的id
     */
    private Long userId;

    /*
     * 登录用户的手机号
     */
    private String phone;

    /*
     * 登录用户的验证码
     */
    private String code;

    /*
     * 登录用户的设备id
     */
    private String deviceId;

    /*
     * 登录用户的ip
     */
    private String ip;

    /*
     * 登录用户的是否自动登录
     */
    private Boolean isAutoLogin;

}
