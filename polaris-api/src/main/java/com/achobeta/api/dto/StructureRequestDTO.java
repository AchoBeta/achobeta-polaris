package com.achobeta.api.dto;

import lombok.*;

/**
 * @author yangzhiyao
 * @description 团队架构请求参数对象
 * @date 2024/11/7
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StructureRequestDTO {
    private String userId;
    private String teamName;
}
