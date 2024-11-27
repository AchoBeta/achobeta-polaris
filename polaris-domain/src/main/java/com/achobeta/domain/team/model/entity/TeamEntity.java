package com.achobeta.domain.team.model.entity;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

/**
 * @author yangzhiyao
 * @description 团队实体类
 * @date 2024/11/27
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamEntity {

    @FieldDesc(name = "团队id")
    private String teamId;

    @FieldDesc(name = "团队名称")
    private String teamName;

    @FieldDesc(name = "团队下所有的角色")
    private Object roles;
}
