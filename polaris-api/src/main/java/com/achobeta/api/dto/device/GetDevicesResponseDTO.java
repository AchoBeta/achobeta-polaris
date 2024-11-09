package com.achobeta.api.dto.device;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author huangwenxing
 * @description
 * @date 2024/11/9
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDevicesResponseDTO {
    /**设备业务id*/
    private String deviceId;
    /**设备名*/
    private String deviceName;
    /**设备ip*/
    private String IP;
    /**上次登录时间*/
    private LocalDateTime lastLoginTime;
    /**是否为本机,true是，false不是*/
    private boolean me;
}
