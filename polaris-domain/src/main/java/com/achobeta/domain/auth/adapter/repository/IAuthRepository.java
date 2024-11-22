package com.achobeta.domain.auth.adapter.repository;

import com.achobeta.domain.auth.model.entity.RoleEntity;

import java.util.Collection;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 认证仓库接口
 * @date 2024/11/22
 */
public interface IAuthRepository {

    /**
     * 查询用户角色
     * @param userId 用户id
     * @return
     */
    List<RoleEntity> queryRoles(String userId, String teamId);

    /**
     * 查询用户权限
     * @param userId 用户id
     * @param roleIds 角色id集合
     * @return
     */
    List<String> queryPermissions(String userId, Collection<String> roleIds);

}
