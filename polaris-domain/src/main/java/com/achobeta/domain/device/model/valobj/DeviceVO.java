package com.achobeta.domain.device.model.valobj;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author huangwenxing
 * @description 渲染设备值对象
 * @data 2024/11/7
 * */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceVO {
        /**设备业务id*/
        private String deviceId;
        /**设备名*/
        private String deviceName;
        /**设备ip*/
        private String IP;
        /**更新时间*/
        private LocalDateTime lastLoginTime;
        /**是否为本机,true是，false不是*/
        private boolean me;
}
