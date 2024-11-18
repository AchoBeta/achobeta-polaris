package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.DevicePO;
import org.apache.ibatis.annotations.Mapper;


import java.time.LocalDateTime;

@Mapper
public interface DeviceMapper {

    /*
     * 插入设备
     * @param devicePO
     */
    void insertDevice(DevicePO devicePO);

    /*
     * 根据userId和ip获取设备
     * @param userId
     * @param ip
     * @return DevicePO
     */
    DevicePO getDeviceByIp(String userId, String ip);

    /*
     * 根据deviceId更新设备
     * @param updateTime
     * @param isDelete
     * @param deviceId
     */
    void updateDevice(String deviceId, LocalDateTime updateTime, int isCancel);

    /*
     * 根据deviceId删除设备
     * @param deviceId
     * @param updateTime
     * @param isDeleted
     */
    void deleteDevice(String deviceId, LocalDateTime updateTime, int isDeleted);

}