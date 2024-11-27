package com.achobeta.domain.auth.adapter.repository;

import com.achobeta.domain.auth.model.entity.RoleEntity;
import com.achobeta.domain.team.model.entity.TeamEntity;

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
    List<String> queryPermissions(String userId, List<String> roleIds, String teamId);

    /**
     * 查询用户拥有查看权限的团队的角色
     * @param userId
     * @return
     */
    List<TeamEntity> queryRoles(String userId);
}
