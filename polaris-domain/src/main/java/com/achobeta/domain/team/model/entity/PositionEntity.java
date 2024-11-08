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
    private String teamName;
    /**
     * 团队架构中的等级 0-根节点/团队 1-一级节点 2-二级节点 3-三级节点 4-四级节点
     */
    private Byte level;
    /**
     * 层级关系的子节点/下级分组
     */
    private List<PositionEntity> subordinates;
}
