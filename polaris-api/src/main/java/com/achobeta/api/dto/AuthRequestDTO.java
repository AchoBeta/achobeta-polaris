package com.achobeta.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yangzhiyao
 * @description AuthRequestDTO 测试鉴权请求DTO
 * @date 2024/11/22
 */
@Data
public class AuthRequestDTO {
    @NotBlank(message = "userId不能为空")
    private String userId;
    @NotBlank(message = "teamId不能为空")
    private String teamId;
}
