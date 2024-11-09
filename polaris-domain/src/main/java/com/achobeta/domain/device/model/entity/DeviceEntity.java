package com.achobeta.domain.device.model.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author huangwenxing
 * @description 设备实体实体
 * @data 2024/11/7
 * */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEntity {
    /**用户id*/
    private String userId;
    /**设备业务id*/
    private String deviceId;
    /**设备名*/
    private String deviceName;
    /**设备ip*/
    private String IP;
    /**更新时间*/
    private LocalDateTime lastLoginTime;
    /**是否为本机*/
    private boolean me;
}
