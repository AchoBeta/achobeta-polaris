package com.achobeta.api.dto;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yangzhiyao
 * @description 删除成员请求数据
 * @date 2024/11/21
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeleteMemberRequestDTO implements Serializable {

    @NotBlank(message = "userId不能为空")
    @FieldDesc(name = "用户ID")
    private String userId;

    @NotBlank(message = "memberId不能为空")
    @FieldDesc(name = "成员ID")
    private String memberId;

    @NotBlank(message = "teamId不能为空")
    @FieldDesc(name = "团队ID")
    private String teamId;

}
