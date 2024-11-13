package com.achobeta.api.dto;

import lombok.*;

/**
 * @author yangzhiyao
 * @description 团队架构请求响应DTO
 * @date 2024/11/7
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryStructureResponseDTO {

    /**
     * 职位/分组业务id
     */
    private String positionId;
    /**
     * 职位/分组名称
     */
    private String positionName;
    /**
     * 团队名称
     */
    private String teamId;
    /**
     * 团队架构中的等级 0-根节点/团队 1-一级节点 2-二级节点 3-三级节点 4-四级节点
     */
    private Byte level;
    /**
     * 层级关系的子节点/下级分组
     */
    private Object subordinates;

}
