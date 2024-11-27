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
     * @param fingerPrinting 设备指纹信息
     * @return
     */
    DeviceEntity getDeviceByFingerPrinting(String userId,String fingerPrinting);

    /**
     * 插入设备
     * @param deviceEntity 设备实体
     */
    void insertDevice(DeviceEntity deviceEntity);

    /**
     * 更新设备
     * @param isCancel 是否登陆
     * @param deviceId 设备id
     */
    void updateDevice(String deviceId, int isCancel);

    /**
     * 删除设备
     * @param deviceId 设备id
     */
    void deleteDevice(String deviceId);

    /**
     * 根据设备id查询用户id
     * @param deviceId 设备id
     * @return 用户id
     */
    String getUserIdByDeviceId(String deviceId);

}
