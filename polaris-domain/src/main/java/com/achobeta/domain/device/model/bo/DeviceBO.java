package com.achobeta.domain.device.model.bo;

import com.achobeta.domain.device.model.entity.DeviceEntity;
import lombok.*;

import java.util.List;
/**
 * @author huangwenxing
 * @description 数据传输
 * @date 2024/11/7
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeviceBO {
    /**设备实体集合*/
    List<DeviceEntity> deviceEntities;
}
