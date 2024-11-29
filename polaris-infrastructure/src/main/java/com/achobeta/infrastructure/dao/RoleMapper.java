package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.RolePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    /**
     * 获取用户在指定团队的角色列表
     * @param userId
     * @param teamId
     * @return
     */
    List<RolePO> listRole(String userId, String teamId);

    /**
     * 给用户添加角色
     * @param userId
     * @param roles
     */
    void addUserRoles(String userId, List<String> roles);

    /**
     * 删除用户的角色
     * @param userId
     */
    void deleteUserRoles(String userId);

    /**
     * 获取指定团队的所有角色列表
     * @param teamId
     * @return
     */
    List<RolePO> listRoleByTeamId(String teamId);

    /**
     * 根据角色名称获取角色的id列表
     * @param roleNames
     * @return
     */
    List<String> listRoleIdsByNames(List<String> roleNames);
}
