package com.achobeta.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 修改团队组织架构请求数据对象
 * @date 2024/1112
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModifyStructureRequestDTO implements Serializable {

    /**
     * 用户ID 用于鉴权
     */
    @NotBlank(message = "用户ID不能为空")
    String userId;
    /**
     * 新增的职位列表，包括父节点的ID,名称,等级，以及新职位名称
     * parentPositionId(可以为空), parentPositionName(非空), parentPositionLevel(非空), newPositionName(非空)
     */
    private List<Object> addPositions;
    /**
     * 需要删除的职位列表，包括职位ID和等级level，均不能为空
     */
    private List<Object> deletePositions;
    /**
     * 团队ID
     */
    @NotBlank(message = "团队ID不能为空")
    private String teamId;
}
