package com.achobeta.infrastructure.adapter.port;

import cn.hutool.core.collection.CollectionUtil;
import com.achobeta.domain.auth.model.entity.PermissionEntity;
import com.achobeta.domain.auth.model.entity.RoleEntity;
import com.achobeta.domain.login.adapter.port.ITeamInfoPort;
import com.achobeta.domain.team.model.entity.TeamEntity;
import com.achobeta.infrastructure.dao.PermissionMapper;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.RoleMapper;
import com.achobeta.infrastructure.dao.po.PermissionPO;
import com.achobeta.infrastructure.dao.po.PositionPO;
import com.achobeta.infrastructure.dao.po.RolePO;
import com.achobeta.infrastructure.redis.IRedisService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.achobeta.types.support.util.BuildKeyUtil.buildUserPermissionInTeamKey;
import static com.achobeta.types.support.util.BuildKeyUtil.buildUserRoleInTeamKey;

/**
 * @author yuangzhiyao
 * @description 查询用户团队信息的外部接口
 * @date 2024/11/23
 */
@Component
public class TeamInfoPort implements ITeamInfoPort {

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private IRedisService redisService;

    @Override
    public List<TeamEntity> queryTeamByUserId(String userId) {
        List<PositionPO> positionPOList = positionMapper.listTeamByUserId(userId);
        if (CollectionUtil.isEmpty(positionPOList)) {
            return Collections.emptyList();
        }
        List<TeamEntity> teams = new ArrayList<>(positionPOList.size());
        for (PositionPO positionPO : positionPOList) {
            String teamId = positionPO.getPositionId();

            // 查询用户角色权限信息
            List<RoleEntity> userRoles = queryRoles(userId, teamId);
            List<String> userRoleIds = new ArrayList<>();
            for (RoleEntity userRole : userRoles) {
                userRoleIds.add(userRole.getRoleId());
            }
            List<String> userPermissions = queryPermissions(userId, userRoleIds, teamId);
            PermissionEntity permissionEntity = setPermissionValues(userPermissions);

            teams.add(TeamEntity.builder()
                    .teamId(positionPO.getPositionId())
                    .teamName(positionPO.getPositionName())
                    .permissions(permissionEntity)
                    .build());
        }
        return teams;
    }

    private List<RoleEntity> queryRoles(String userId, String teamId) {
        List<RoleEntity> roleEntityList = redisService.getValue(buildUserRoleInTeamKey(userId, teamId));
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

        redisService.setValue(buildUserRoleInTeamKey(userId, teamId), roleEntityList);
        return roleEntityList;
    }

    private List<String> queryPermissions(String userId, List<String> roleIds, String teamId) {
        List<String> permissionNames = redisService.getValue(buildUserPermissionInTeamKey(userId, teamId));
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

        redisService.setValue(buildUserPermissionInTeamKey(userId, teamId), permissionNames);
        return permissionNames;
    }

    private PermissionEntity setPermissionValues(List<String> userPermissions) {
        PermissionEntity permissionEntity = new PermissionEntity();
        for (String permission : userPermissions) {
            switch (permission) {
                case "SUPER":
                    permissionEntity.setSUPER(1);
                    break;
                case "MEMBER":
                    permissionEntity.setMEMBER(1);
                    break;
                case "STRUCTURE":
                    permissionEntity.setSTRUCTURE(1);
                    break;
                case "USER":
                    permissionEntity.setUSER(1);
                    break;
                case "AUTH":
                    permissionEntity.setAUTH(1);
                    break;
                case "MEMBER_MODIFY":
                    permissionEntity.setMEMBER_MODIFY(1);
                    break;
                case "MEMBER_LIST":
                    permissionEntity.setMEMBER_LIST(1);
                    break;
                case "MEMBER_ADD":
                    permissionEntity.setMEMBER_ADD(1);
                    break;
                case "MEMBER_DELETE":
                    permissionEntity.setMEMBER_DELETE(1);
                    break;
                case "MEMBER_DETAIL":
                    permissionEntity.setMEMBER_DETAIL(1);
                    break;
                case "STRUCTURE_MODIFY":
                    permissionEntity.setSTRUCTURE_MODIFY(1);
                    break;
                case "STRUCTURE_VIEW":
                    permissionEntity.setSTRUCTURE_VIEW(1);
                    break;
                case "TEAM_ADD":
                    permissionEntity.setTEAM_ADD(1);
                    break;
                case "TEAM_DELETE":
                    permissionEntity.setTEAM_DELETE(1);
                    break;
                case "ROLE_LIST":
                    permissionEntity.setROLE_LIST(1);
                    break;
                default:
                    break;
            }
        }
        return permissionEntity;
    }
}
