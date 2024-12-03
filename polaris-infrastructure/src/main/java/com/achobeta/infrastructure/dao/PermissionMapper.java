package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.PermissionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {

    /**
     * 通过用户ID和角色ID列表查询权限列表
     * @param userId
     * @param roleIds
     * @return
     */
    List<PermissionPO> listPermission(@Param("userId") String userId,@Param("roleIds") List<String> roleIds);

}
