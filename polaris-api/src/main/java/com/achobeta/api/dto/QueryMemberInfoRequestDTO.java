package com.achobeta.api.dto;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yangzhiyao
 * @description 查看团队成员信息详情请求DTO
 * @date 2024/11/19
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryMemberInfoRequestDTO implements Serializable {

    @NotBlank(message = "用户ID不能为空")
    @FieldDesc(name = "用户ID")
    private String userId;

    @NotBlank(message = "团队ID不能为空")
    @FieldDesc(name = "团队ID")
    private String teamId;

    @NotBlank(message = "成员ID不能为空")
    @FieldDesc(name = "成员ID")
    private String memberId;
}
