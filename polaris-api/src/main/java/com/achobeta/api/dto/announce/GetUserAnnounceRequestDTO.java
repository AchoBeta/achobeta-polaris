package com.achobeta.api.dto.announce;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author huangwenxing
 * @date 2024/11/11
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserAnnounceRequestDTO {
    @NotBlank(message = "获取公告必须指定用户id")
    @FieldDesc(name = "用户id")
    private String userId;
    @NotNull(message = "获取公告必须指定单页长度")
    @FieldDesc(name = "单页长度")
    private int limit;
    @FieldDesc(name = "单页最后一个公告id")
    private String lastAnnounceId;
}
