package com.achobeta.domain.team.model.entity;

import lombok.*;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 职位实体类
 * @date 2024/11/7
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionEntity {
    /**
     * 父级职位/分组ID
     */
    private String parentPositionId;
    /**
     * 职位/分组业务id
     */
    private String positionId;
    /**
     * 职位/分组名称
     */
    private String positionName;
    /**
     * 团队ID
     */
    private String teamId;
    /**
     * 团队ID
     */
    private String teamName;
    /**
     * 团队架构中的等级 0-根节点/团队 1-一级节点 2-二级节点 3-三级节点 4-四级节点
     */
    private Integer level;
    /**
     * 层级关系的子节点/下级分组
     */
    private List<PositionEntity> subordinates;
    /**
     * 添加新节点时用到，子节点ID
     */
    private String subordinateId;
    /**
     * 添加新节点时用到，子节点名称，用来找新生成的它的ID
     */
    private String subordinateName;
}
