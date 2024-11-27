package com.achobeta.domain.login.model.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author: 严豪哲
 * @Description: 设备实体类
 * @Date: 2024/11/12 21:16
 * @Version: 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceEntity {

    /*
     * 设备业务id
     */
    private String deviceId;
    /*
     * 设备名称
     */
    private String deviceName;
    /*
     * 用户业务id
     */
    private String userId;
    /*
     * 设备ip地址
     */
    private String IP;
    /*
     * 创建时间
     */
    private LocalDateTime createTime;
    /*
     *更新时间
     */
    private LocalDateTime updateTime;
    /*
     * 是否自动登录
     */
    private int isCancel;

    /**
     * 设备的指纹信息
     */
    private String fingerPrinting;

    /**
     * 更新时间
     * */
    private LocalDateTime lastLoginTime;

    /**
     * 是否为本机
     */
    private boolean me;
}
