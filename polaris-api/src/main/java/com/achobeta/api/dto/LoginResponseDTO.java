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
     * 登录用户的角色
     */
    private String role;
}
