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
public class GetUserAnnounceCountRequestDTO {
    @NotBlank(message = "获取公告数量必须指定用户id")
    @FieldDesc(name = "用户id")
    private String userId;
}
