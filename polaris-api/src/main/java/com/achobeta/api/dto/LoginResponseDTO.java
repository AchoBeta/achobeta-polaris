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
     * 登录用户的职位列表
     * 实际为List<PositionEntity>，PositionEntity包括该该类中的其他字段
     */
    private Object positionList;

    /**
     * 登录用户的设备id
     */
    private String deviceId;

}
