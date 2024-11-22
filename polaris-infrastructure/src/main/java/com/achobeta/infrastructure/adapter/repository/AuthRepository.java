package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.auth.adapter.repository.IAuthRepository;
import com.achobeta.domain.auth.model.entity.RoleEntity;
import com.achobeta.infrastructure.dao.PermissionMapper;
import com.achobeta.infrastructure.dao.RoleMapper;
import com.achobeta.infrastructure.dao.po.PermissionPO;
import com.achobeta.infrastructure.dao.po.RolePO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author yangzhiyao
 * @description AuthRepository
 * @date 2024/11/22
 */
@Repository
public class AuthRepository implements IAuthRepository {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<RoleEntity> queryRoles(String userId, String teamId) {
        List<RolePO> rolePOList = roleMapper.listRole(userId, teamId);
        List<RoleEntity> roleEntityList = new ArrayList<>(rolePOList.size());

        for (RolePO rolePO : rolePOList) {
            roleEntityList.add(RoleEntity.builder()
                    .roleId(rolePO.getRoleId())
                    .roleName(rolePO.getRoleName())
                    .createTime(rolePO.getCreateTime())
                    .updateTime(rolePO.getUpdateTime())
                    .build());
        }

        return roleEntityList;
    }

    @Override
    public List<String> queryPermissions(String userId, List<String> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<PermissionPO> permissions = permissionMapper.listPermission(userId, roleIds);
        List<String> permissionNames = new ArrayList<>(permissions.size());
        for (PermissionPO permission : permissions) {
            permissionNames.add(permission.getPermissionName());
        }

        return permissionNames;
    }
}
