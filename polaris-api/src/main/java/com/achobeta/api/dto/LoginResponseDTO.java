package com.achobeta.api.dto;

import lombok.*;

/**
 * @Author: 严豪哲
 * @Description: 登录响应DTO
 * @Date: 2024/11/9 10:08
 * @Version: 1.0
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    /**
     * 登录用户的手机号
     */
    private String phone;

    /**
     * 登录用户的accessToken
     */
    private String accessToken;

    /**
     * 登录用户的refreshToken
     */
    private String refreshToken;

    /**
     * 登录用户的角色
     */
    private String role;

    /**
     * 登录用户的id
     */
    private String userId;

    /**
     * 登录用户所属的团队信息
     * 实际为List<TeamEntity>，TeamEntity包括该该类中的其他字段
     */
    private Object teams;

    /**
     * 登录用户的设备id
     */
    private String deviceId;

}
