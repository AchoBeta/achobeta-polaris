package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.DevicePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author huangwenxing
 * @description 设备Dao接口
 * @data 2024/11/7
 * */
@Mapper
public interface DeviceMapper {
    /**
     *
     * @param userId 用户ID
     * @param lastDeviceId 单页最后一台设备ID
     * @param limit 表行数
     * @return 设备PO集合
     */
    List<DevicePO> queryCommonUseAutoLoginDevicesById(String userId,int limit,String lastDeviceId);
    Integer queryCommonUserDeviceCountById(String userId,int limit,String lastDeviceId);
}
