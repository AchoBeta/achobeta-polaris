package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.device.adapter.repository.IDeviceRepository;
import com.achobeta.domain.device.model.entity.DeviceEntity;
import com.achobeta.infrastructure.dao.DeviceMapper;
import com.achobeta.infrastructure.dao.po.DevicePO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author huangwenxing
 * @description
 * @date 2024/11/6
 */
@Repository
@Slf4j
public class DeviceRepository implements IDeviceRepository {
    @Resource
    private DeviceMapper deviceMapper;

    /**
     *
     * @param userId 用户ID
     * @param lastDeviceId 单页最后一台设备
     * @param limit 表行数
     * @return 登录设备Entity集合
     */
    @Override
    public List<DeviceEntity> queryCommonUseAutoLoginDevicesById(String userId, int limit, String lastDeviceId) {
        List<DevicePO> devicesPO = deviceMapper.queryCommonUseAutoLoginDevicesById(userId,limit,lastDeviceId);
        return devicesPO.stream()
                .map(devicePO -> DeviceEntity.builder()
                        .deviceId(devicePO.getDeviceId())
                        .deviceName(devicePO.getDeviceName())
                        .IP(devicePO.getIP())
                        .lastLoginTime(devicePO.getUpdateTime())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     *
     * @param userId
     * @param limit
     * @param lastDeviceId
     * @return 查询该参数下，设备数
     */
    @Override
    public Integer queryCommonUserDeviceCountById(String userId, int limit, String lastDeviceId) {
        return deviceMapper.queryCommonUserDeviceCountById(userId, limit, lastDeviceId);
    }
}
