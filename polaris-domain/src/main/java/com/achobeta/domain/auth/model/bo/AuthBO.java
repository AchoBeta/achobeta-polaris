package com.achobeta.domain.auth.model.bo;

import com.achobeta.domain.auth.model.entity.PermissionEntity;
import com.achobeta.domain.auth.model.entity.RoleEntity;
import lombok.*;

/**
 * @author yangzhiyao
 * @description AuthBO
 * @date 2024/11/22
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthBO {

    private String teamId;
    private String userId;
    private RoleEntity roleEntity;
    private PermissionEntity permissionEntity;

}
