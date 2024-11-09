package com.achobeta.domain.device.service;

import com.achobeta.domain.device.model.valobj.DeviceVO;

import java.util.List;

/**
 * @author huangwenxing
 * @description 设备渲染服务统一接口
 * @date 2024/11/8
 */
public interface IDeviceTextService {
    /**获取单页设备List*/
    List<DeviceVO> getDeviceVO(String userId,String deviceId,int limit,String lastDeviceId);
}
