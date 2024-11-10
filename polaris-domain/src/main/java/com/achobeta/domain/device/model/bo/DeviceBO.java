package com.achobeta.domain.device.model.bo;

import com.achobeta.domain.device.model.entity.PageResult;
import com.achobeta.domain.device.model.entity.UserCommonDevicesEntities;
import lombok.*;
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
    /**设备常用设备实体*/
    private UserCommonDevicesEntities userCommonDevicesEntities;
    /**单页数据实体*/
    private PageResult pageResult;
}
