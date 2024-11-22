package com.achobeta.api.dto;

import lombok.Data;

/**
 * @author yangzhiyao
 * @description AuthRequestDTO 测试鉴权请求DTO
 * @date 2024/11/22
 */
@Data
public class AuthRequestDTO {
    private String userId;
    private String teamId;
}
