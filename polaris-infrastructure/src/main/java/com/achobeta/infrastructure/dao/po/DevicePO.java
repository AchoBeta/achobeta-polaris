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

    /**
     * 设备业务id
     */
    private String deviceId;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 用户业务id
     */
    private String userId;
    /**
     * 设备ip地址
     */
    private String ip;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     *更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 是否自动登录
     */
    private Integer isCancel;

    /**
     * 设备mac地址
     */
    private String mac;

}
