package com.achobeta.domain.device.model.entity;

import lombok.*;

import java.util.List;

/**
 * @author huangwenxing
 * @description 用户常用设备实体
 * @data 2024/11/9
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCommonDevicesEntities {
    /**用户id*/
    private String userId;
    /**用户发起请求的设备id*/
    private String deviceId;
    /**用户常用设备*/
    private List<DeviceEntity> deviceEntities;
}
