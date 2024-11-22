package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.RolePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<RolePO> listRole(String userId, String teamId);

}
