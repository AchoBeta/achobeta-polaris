package com.achobeta.api.dto.announce;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.NotBlank;
/**
 * @author huangwenxing
 * @date 2024/11/17
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadAllAnnounceRequestDTO {
    @NotBlank(message = "读取公告必须指定用户ID")
    @FieldDesc(name = "用户ID")
    private String userId;
}
