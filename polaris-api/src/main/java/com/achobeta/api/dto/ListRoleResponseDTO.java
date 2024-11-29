package com.achobeta.api.dto;

import lombok.*;

/**
 * @author yangzhiyao
 * @description 查询团队及其角色的数据响应
 * @date 2024/11/27
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListRoleResponseDTO {

    /**
     * 实质是一个List<RoleEntity>
     * RoleEntity(roleId, roleName)
     */
    private Object roles;
}
