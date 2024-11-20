package com.achobeta.domain.device.model.valobj;

import com.achobeta.domain.device.model.entity.DeviceEntity;
import lombok.*;

import java.util.List;

/**
 * @author huangwenxing
 * @description 用户常用设备VO
 * @data 2024/11/10
 * * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCommonDevicesVO {
    /**用户常用设备*/
    private List<DeviceEntity> deviceEntities;
    /**是否拥有下一页*/
    private boolean more;
}
