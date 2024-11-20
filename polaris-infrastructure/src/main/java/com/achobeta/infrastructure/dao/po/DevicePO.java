package com.achobeta.infrastructure.dao.po;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author huangwenxing
 * @description 设备持久化对象
 * @data 2024/11/7
 * */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DevicePO {
    /**主键id*/
    private long id;
    /**设备业务id*/
    private String deviceId;
    /**设备名*/
    private String deviceName;
    /**用户业务id*/
    private String userId;
    /**设备ip*/
    private String IP;
    /**创建时间*/
    private LocalDateTime createTime;
    /**更新时间*/
    private LocalDateTime updateTime;
}
