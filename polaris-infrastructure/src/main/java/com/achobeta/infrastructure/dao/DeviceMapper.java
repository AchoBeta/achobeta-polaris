package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.DevicePO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huangwenxing
 * @description 设备Dao接口
 * @data 2024/11/7
 * */
@Mapper
public interface DeviceMapper {

    /**
     * 插入设备
     * @param devicePO
     */
    void insertDevice(DevicePO devicePO);

    /**
     * 根据userId和ip获取设备
     * @param userId
     * @param mac
     * @return DevicePO
     */
    DevicePO getDeviceByFingerPrinting(String userId, String fingerPrinting);

    /**
     * 根据deviceId更新设备
     * @param updateTime
     * @param isCancel
     * @param deviceId
     */
    void updateDevice(String deviceId, LocalDateTime updateTime, int isCancel);

    /**
     * 根据deviceId删除设备
     * @param deviceId
     * @param updateTime
     * @param isDeleted
     */
    void deleteDevice(String deviceId, LocalDateTime updateTime, int isDeleted);

    /**
     *
     * @param userId 用户ID
     * @param lastDeviceId 单页最后一台设备ID
     * @param limit 表行数
     * @return 设备PO集合
     */
    List<DevicePO> queryCommonUseAutoLoginDevicesById(String userId, int limit, String lastDeviceId);
    Integer queryCommonUserDeviceCountById(String userId,int limit,String lastDeviceId);

    /*
     * 根据deviceId获取userId
     * @param deviceId
     * @return userId
     */
    DevicePO getUserIdByDeviceId(String deviceId);

}
