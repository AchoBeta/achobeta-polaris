package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.PermissionPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PermissionMapper {

    List<PermissionPO> listPermission(String userId, Collection<String> roleIds);

}
