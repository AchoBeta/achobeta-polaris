package com.achobeta.api.dto;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @author yangzhiyao
 * @description 查询团队及其角色的请求DTO
 * @date 2024/11/27
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListRoleRequestDTO {

    @NotBlank(message = "用户id不能为空")
    @FieldDesc(name = "用户id")
    private String userId;

}
