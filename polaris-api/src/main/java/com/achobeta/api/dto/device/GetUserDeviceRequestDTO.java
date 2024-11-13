package com.achobeta.api.dto.device;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author huangwenxing
 * @date 2024/11/9
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDeviceRequestDTO implements Serializable {
    @NotNull
    @FieldDesc(name = "用户id")
    private String userId;
    @NotNull
    @FieldDesc(name = "设备id")
    private String deviceId;
    @NotNull
    @FieldDesc(name = "单页长度")
    private int limit;
    @NotNull
    @FieldDesc(name = "单页最后一个设备id")
    private String lastDeviceId;
}
