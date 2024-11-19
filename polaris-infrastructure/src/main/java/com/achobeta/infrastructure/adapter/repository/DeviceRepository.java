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

    @Override
    public DeviceEntity getDeviceByIp(String userId, String ip) {

        DevicePO devicePO = null;
        devicePO = deviceMapper.getDeviceByIp(userId, ip);

        if (devicePO == null) {
            return null;
        }
        return DeviceEntity.builder()
                .deviceId(devicePO.getDeviceId())
                .deviceName(devicePO.getDeviceName())
                .userId(devicePO.getUserId())
                .IP(devicePO.getIp())
                .createTime(devicePO.getCreateTime())
                .updateTime(devicePO.getUpdateTime())
                .build();
    }

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
               .build();
        deviceMapper.insertDevice(devicePO);

    }

    @Override
    public void updateDevice(String deviceId, LocalDateTime updateTime, int isCancel){
        deviceMapper.updateDevice(deviceId, updateTime, isCancel);
    }

    @Override
    public void deleteDevice(String deviceId, LocalDateTime updateTime, int isDeleted) {
        deviceMapper.deleteDevice(deviceId, updateTime, isDeleted);
    }

    @Override
    public String getUserIdByDeviceId(String deviceId) {
        DevicePO devicePO = deviceMapper.getUserIdByDeviceId(deviceId);
        if (devicePO == null) {
            return null;
        }
        return devicePO.getUserId();
    }
}
