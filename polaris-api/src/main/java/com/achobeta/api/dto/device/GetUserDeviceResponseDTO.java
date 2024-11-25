package com.achobeta.api.dto.device;

import lombok.*;

import java.util.List;

/**
 * @author huangwenxing
 * @description
 * @date 2024/11/9
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDeviceResponseDTO {
    /**用户常用设备集合*/
    private List userCommonUseDevices;
    /**是否还有更多数据*/
    private boolean more;
}
