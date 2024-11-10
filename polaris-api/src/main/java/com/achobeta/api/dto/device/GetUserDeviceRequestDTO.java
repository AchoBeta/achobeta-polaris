package com.achobeta.api.dto.device;

import lombok.*;

/**
 * @author huangwenxing
 * @date 2024/11/9
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDeviceRequestDTO {
    /**用户id*/
    private String userId;
    /**设备id*/
    private String deviceId;
    /**单页长度*/
    private int limit;
    /**单页最后一个设备id*/
    private String lastDeviceId;
}
