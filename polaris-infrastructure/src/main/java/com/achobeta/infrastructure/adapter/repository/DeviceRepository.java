package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.login.adapter.repository.IDeviceRepository;
import com.achobeta.domain.login.model.entity.DeviceEntity;
import com.achobeta.infrastructure.dao.DeviceMapper;
import com.achobeta.infrastructure.dao.po.DevicePO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 严豪哲
 * @Description: 设备仓储层接口
 * @Date: 2024/11/12 19:13
 * @Version: 1.0
 */
@Repository
public class DeviceRepository implements com.achobeta.domain.login.adapter.repository.IDeviceRepository , com.achobeta.domain.device.adapter.repository.IDeviceRepository {

    @Resource
    private DeviceMapper deviceMapper;

    private static final int DELETED = 1;

    /*
     * 根据用户id和ip地址查询设备信息
     * @param userId 用户id
     * @param ip ip地址
     * @return DeviceEntity 设备信息
     */
    @Override
    public DeviceEntity getDeviceByFingerPrinting(String userId, String fingerPrinting) {

        DevicePO devicePO = deviceMapper.getDeviceByFingerPrinting(userId, fingerPrinting);
        if (devicePO == null) {
            return null;
        }
        return DeviceEntity.builder()
                .deviceId(devicePO.getDeviceId())
                .deviceName(devicePO.getDeviceName())
                .userId(devicePO.getUserId())
                .IP(devicePO.getIp())
                .fingerPrinting(devicePO.getFingerPrinting())
                .createTime(devicePO.getCreateTime())
                .updateTime(devicePO.getUpdateTime())
                .build();
    }

    /*
     * 插入设备信息
     * @param deviceEntity 设备信息
     */
    @Override
    public void insertDevice(DeviceEntity deviceEntity) {

        DevicePO devicePO = DevicePO.builder()
                .deviceId(deviceEntity.getDeviceId())
                .deviceName(deviceEntity.getDeviceName())
                .userId(deviceEntity.getUserId())
                .ip(deviceEntity.getIP())
                .createTime(deviceEntity.getCreateTime())
                .updateTime(deviceEntity.getUpdateTime())
                .isCancel(deviceEntity.getIsCancel())
                .fingerPrinting(deviceEntity.getFingerPrinting())
                .build();
        deviceMapper.insertDevice(devicePO);

    }

    /*
     * 更新设备
     * @param deviceId 设备id
     * @param updateTime 更新时间
     * @param isCancel 是否自动登陆
     */
    @Override
    public void updateDevice(String deviceId, int isCancel) {
        deviceMapper.updateDevice(deviceId, LocalDateTime.now(), isCancel);
    }

    /*
     * 删除设备
     * @param deviceId
     * @param updateTime
     * @param isDeleted
     */
    @Override
    public void deleteDevice(String deviceId) {
        deviceMapper.deleteDevice(deviceId, LocalDateTime.now(), DELETED);
    }

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
                        .IP(devicePO.getIp())
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


    @Override
    public String getUserIdByDeviceId(String deviceId) {
        DevicePO userPO = deviceMapper.getUserIdByDeviceId(deviceId);
        if (userPO == null) {
            return null;
        }
        return userPO.getUserId();
    }
}
