package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.login.adapter.repository.IDeviceRepository;
import com.achobeta.domain.login.model.entity.DeviceEntity;
import com.achobeta.infrastructure.dao.DeviceMapper;
import com.achobeta.infrastructure.dao.po.DevicePO;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author: 严豪哲
 * @Description: 设备仓储层接口
 * @Date: 2024/11/12 19:13
 * @Version: 1.0
 */
@Repository
public class DeviceRepository implements IDeviceRepository {

    @Resource
    private DeviceMapper deviceMapper;

    /*
     * 根据用户id和ip地址查询设备信息
     * @param userId 用户id
     * @param ip ip地址
     * @return DeviceEntity 设备信息
     */
    @Override
    public DeviceEntity getDeviceByMac(String userId, String mac) {

        DevicePO devicePO = deviceMapper.getDeviceByMac(userId, mac);
        if (devicePO == null) {
            return null;
        }
        return DeviceEntity.builder()
                .deviceId(devicePO.getDeviceId())
                .deviceName(devicePO.getDeviceName())
                .userId(devicePO.getUserId())
                .IP(devicePO.getIp())
                .mac(devicePO.getMac())
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
                .mac(deviceEntity.getMac())
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
}
