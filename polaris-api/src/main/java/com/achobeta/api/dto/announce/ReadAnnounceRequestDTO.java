package com.achobeta.api.dto.announce;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
/**
 * @author huangwenxing
 * @date 2024/11/13
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadAnnounceRequestDTO implements Serializable {
    @NotBlank(message = "修改必须指定用户ID")
    @FieldDesc(name = "用户ID")
    private String userId;
    @NotBlank(message = "修改必须指定公告ID")
    @FieldDesc(name = "用户ID")
    private String announceId;
}
