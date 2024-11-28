package com.achobeta.api.dto.like;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author huangwenxing
 * @date 2024/11/17
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequestDTO {
    @NotBlank(message = "点赞的用户id不能为空")
    @FieldDesc(name = "点赞人id")
    private String fromId;
    @NotBlank(message = "获赞的人id不能为空")
    @FieldDesc(name = "获赞人id")
    private String toId;
    @NotNull(message = "点赞状态不能为空")
    @FieldDesc(name = "点赞状态")
    private boolean liked;
}
