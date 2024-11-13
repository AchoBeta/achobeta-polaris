package com.achobeta.api.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author yangzhiyao
 * @description 修改团队组织架构响应数据对象
 * @date 2024/11/12
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyStructureResponseDTO implements Serializable {

    /**
     * 真正的添加节点数据
     */
    Object addPositions;
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
     * 团队架构中的等级 0-根节点/团队 1-一级节点 2-二级节点 3-三级节点 4-四级节点
     */
    private Byte level;
    /**
     * 添加新节点时用到，子节点ID
     */
    private String subordinateId;
    /**
     * 添加新节点时用到，子节点名称，用来找新生成的它的ID
     */
    private String subordinateName;

}
