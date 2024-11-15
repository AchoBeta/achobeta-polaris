package com.achobeta.api.dto.device;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "读取设备用户id不能为空")
    @FieldDesc(name = "用户id")
    private String userId;
    @NotBlank(message = "读取设备id不能为空")
    @FieldDesc(name = "设备id")
    private String deviceId;
    @NotNull(message = "读取单页长度不能为空")
    @FieldDesc(name = "单页长度")
    private Integer limit;
    @FieldDesc(name = "单页最后一个设备id")
    private String lastDeviceId;
}
