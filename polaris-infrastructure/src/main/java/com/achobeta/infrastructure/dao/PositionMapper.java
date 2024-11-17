package com.achobeta.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 职位/分组dao
 * @date 2024/11/7
 */
@Mapper
public interface PositionMapper {

    void addPositionToUser(List<String> positionList, String userId);
}