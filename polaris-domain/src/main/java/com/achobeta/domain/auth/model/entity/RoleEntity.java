package com.achobeta.domain.auth.model.entity;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author yangzhiyao
 * @description 角色实体对象
 * @date 2024/11/22
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {

    @FieldDesc(name ="角色id")
    private String roleId;

    @FieldDesc(name ="角色名称")
    private String roleName;

    @FieldDesc(name = "团队id")
    private String teamId;

    @FieldDesc(name = "创建时间")
    private LocalDateTime createTime;

    @FieldDesc(name = "更新时间")
    private LocalDateTime updateTime;

}
