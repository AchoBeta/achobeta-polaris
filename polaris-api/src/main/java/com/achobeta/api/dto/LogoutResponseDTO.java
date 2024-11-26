package com.achobeta.api.dto;

import lombok.*;

/**
 * @Author: 严豪哲
 * @Description: 登出响应DTO
 * @Date: 2024/11/19 19:05
 * @Version: 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutResponseDTO {

    /**
     * 设备id
     */
    private String deviceId;
}
