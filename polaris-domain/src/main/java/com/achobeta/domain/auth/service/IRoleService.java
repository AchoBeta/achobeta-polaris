package com.achobeta.domain.auth.service;

import com.achobeta.domain.team.model.entity.TeamEntity;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 角色服务接口
 * @date 2024/11/27
 */
public interface IRoleService {

    /**
     * 根据用户ID查询有对应权限的团队的角色列表
     * @param userId 用户ID
     * @return 团队及其角色列表
     */
    public List<TeamEntity> queryRoles(String userId);
}
