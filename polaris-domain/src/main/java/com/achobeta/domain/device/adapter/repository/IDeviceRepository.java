package com.achobeta.domain.device.adapter.repository;

import com.achobeta.domain.login.model.entity.DeviceEntity;

import java.util.List;
/**
 * @author huangwenxing
 * @description 查询设备仓储接口
 * @date 2024/11/7
 */

public interface IDeviceRepository {
    /**
     *
     * @param userId 用户ID
     * @param lastDeviceId 单页最后一台设备ID
     * @param limit 表行数
     * @return 设备实体集合
     */
    List<DeviceEntity> queryCommonUseAutoLoginDevicesById(String userId, int limit, String lastDeviceId);
    Integer queryCommonUserDeviceCountById(String userId, int limit, String lastDeviceId);
}
