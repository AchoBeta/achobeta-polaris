package com.achobeta.domain.login.adapter.repository;


import com.achobeta.domain.login.model.entity.DeviceEntity;

import java.time.LocalDateTime;

/**
 * @Author: 严豪哲
 * @Description: 设备仓储层接口定义
 * @Date: 2024/11/12 19:13
 * @Version: 1.0
 */

public interface IDeviceRepository {

    /**
     * 设备信息查询
     * @param userId 用户id
     * @param mac ip地址
     * @return
     */
    DeviceEntity getDeviceByMac(String userId,String mac);

    /*
     * 插入设备
     * @param deviceEntity 设备实体
     */
    void insertDevice(DeviceEntity deviceEntity);

    /*
     * 更新设备
     * @param updateTime 更新时间
     * @param isCancel 是否登陆
     * @param deviceId 设备id
     */
    void updateDevice(String deviceId, LocalDateTime updateTime, int isCancel);

    /*
     * 删除设备
     * @param deviceId 设备id
     * @param updateTime 更新时间
     * @param isDeleted 是否删除
     */
    void deleteDevice(String deviceId, LocalDateTime updateTime, int isDeleted);

}
