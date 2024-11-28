package com.achobeta.infrastructure.adapter.repository;

import cn.hutool.core.collection.CollectionUtil;
import com.achobeta.domain.auth.adapter.repository.IAuthRepository;
import com.achobeta.domain.auth.model.entity.RoleEntity;
import com.achobeta.domain.team.model.entity.TeamEntity;
import com.achobeta.infrastructure.dao.PermissionMapper;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.RoleMapper;
import com.achobeta.infrastructure.dao.po.PermissionPO;
import com.achobeta.infrastructure.dao.po.PositionPO;
import com.achobeta.infrastructure.dao.po.RolePO;
import com.achobeta.infrastructure.redis.IRedisService;
import com.achobeta.types.common.RedisKey;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private IRedisService redisService;

    @Override
    public List<RoleEntity> queryRoles(String userId, String teamId) {
        List<RoleEntity> roleEntityList = redisService.getValue(RedisKey.USER_ROLE_IN_TEAM + userId + teamId);
        if (!CollectionUtil.isEmpty(roleEntityList)) {
            return roleEntityList;
        }

        List<RolePO> rolePOList = roleMapper.listRole(userId, teamId);
        roleEntityList = new ArrayList<>(rolePOList.size());

        for (RolePO rolePO : rolePOList) {
            roleEntityList.add(RoleEntity.builder()
                    .roleId(rolePO.getRoleId())
                    .roleName(rolePO.getRoleName())
                    .build());
        }

        redisService.setValue(RedisKey.USER_ROLE_IN_TEAM + userId + teamId, roleEntityList);
        return roleEntityList;
    }

    @Override
    public List<String> queryPermissions(String userId, List<String> roleIds, String teamId) {
        List<String> permissionNames = redisService.getValue(RedisKey.USER_PERMISSION_IN_TEAM + userId);
        if (!CollectionUtil.isEmpty(permissionNames)) {
            return permissionNames;
        }

        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<PermissionPO> permissions = permissionMapper.listPermission(userId, roleIds);
        permissionNames = new ArrayList<>(permissions.size());
        for (PermissionPO permission : permissions) {
            permissionNames.add(permission.getPermissionName());
        }

        redisService.setValue(RedisKey.USER_PERMISSION_IN_TEAM + userId, permissionNames);
        return permissionNames;
    }

    @Override
    public List<TeamEntity> queryRoles(String userId) {

        List<TeamEntity> resultTeamList = new ArrayList<>();
        List<PositionPO> teams = positionMapper.listTeamByUserId(userId);
        for (PositionPO team : teams) {
            String teamId = team.getPositionId();
            List<RoleEntity> roleEntityList = redisService.getValue(RedisKey.TEAM_ROLE + teamId);
            if (roleEntityList == null) {
                roleEntityList = new ArrayList<>();
                List<RolePO> rolePOList = roleMapper.listRoleByTeamId(teamId);
                for (RolePO rolePO : rolePOList) {
                    roleEntityList.add(RoleEntity.builder()
                            .roleId(rolePO.getRoleId())
                            .roleName(rolePO.getRoleName())
                            .build());
                }
                redisService.setValue(RedisKey.TEAM_ROLE + teamId, roleEntityList);
            }
            resultTeamList.add(TeamEntity.builder()
                    .teamId(team.getPositionId())
                    .teamName(team.getPositionName())
                    .roles(roleEntityList)
                    .build());
        }
        return resultTeamList;
    }
}
