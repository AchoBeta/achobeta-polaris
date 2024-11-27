package com.achobeta.api.dto;

import lombok.*;

import javax.validation.constraints.Pattern;

/**
 * @Author: 严豪哲
 * @Description: 登出请求DTO
 * @Date: 2024/11/19 19:04
 * @Version: 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequestDTO {

    /**
     * 设备id (可选)
     */
    @Pattern(regexp = "^[0-9]{15}$|^$")
    String deviceId;
}
