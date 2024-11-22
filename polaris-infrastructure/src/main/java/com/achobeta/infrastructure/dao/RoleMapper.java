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

}
