package com.achobeta.infrastructure.dao.po;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author yangzhiyao
 * @description
 * @date 2024/11/7
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PositionPO {
    /**
     * 主键
     */
    private Long id;
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
     * 层级关系的子节点/下级分组业务id
     */
    private String subordinate;
    /**
     * 创建者userId
     */
    private String createBy;
    /**
     * 更新者userId
     */
    private String updateBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
