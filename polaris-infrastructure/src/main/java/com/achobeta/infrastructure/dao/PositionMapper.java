package com.achobeta.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author yangzhiyao
 * @description 职位/分组dao
 * @date 2024/11/7
 */
@Mapper
public interface PositionMapper {

    /**
     * 获取某个职位的上级职位
     * 用以查询用户的个人职位
     * @param positions
     * @return
     */
    List<PositionPO> listParentPositionByPositions(Collection<PositionPO> positions);

    /**
     * 获取某个用户的职位列表
     * @param userId
     * @return
     */
    List<PositionPO> listPositionByUserId(String userId);
}