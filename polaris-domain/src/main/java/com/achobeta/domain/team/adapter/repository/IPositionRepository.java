package com.achobeta.domain.team.adapter.repository;

import com.achobeta.domain.team.model.entity.PositionEntity;

import java.util.Collection;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 职位仓储接口
 * @date 2024/11/7
 */
public interface IPositionRepository {

    /**
     * 查询某职位/分组的子职位/分组
     * @author yangzhiyao
     * @date 2024/11/11
     * @param positionId
     * @return
     */
    List<PositionEntity> querySubordinatePosition(String positionId, String teamId);

    /**
     * 查询团队是否存在
     * @author yangzhiyao
     * @date 2024/11/11
     * @param teamId 团队ID
     * return Boolean 是否存在该团队
     */
    Boolean isTeamExists(String teamId);

    /**
     * 批量添加职位/分组
     * @author yangzhiyao
     * @date 2024/11/11
     * @param positionEntityList
     */
    void savePosition(List<PositionEntity> positionEntityList, String teamId);

    /**
     * 批量删除职位/分组
     * @author yangzhiyao
     * @date 2024/11/11
     * @param positionsToDelete
     */
    void deletePosition(Collection<String> positionsToDelete, String teamId);

    /**
     * 查询某职位/分组的父节点
     * 删除position要用，查询用户position要用
     * @author yangzhiyao
     * @date 2024/11/14
     * @param positionId
     */
    PositionEntity queryParentPosition(String positionId);

    /**
     * 通过一系列positionId查询所有userId
     * 用于删除position
     * @author yangzhiyao
     */
    List<String> queryUserIdsByPositionIds(Collection<String> positionIds);

    /**
     * 给予成员多个职位/分组,绑定多个user到一个position
     * 删除position要用
     * @author yangzhiyao
     * @date 2024/11/14
     * @param positionId
     * @param userIds
     * @return
     */
    void bindUsersToPosition(String positionId, List<String> userIds);

    /**
     * 查询某个用户的团队Id及名称
     * @param userId
     * @return
     */
    List<PositionEntity> queryTeamByUserId(String userId);

    /**
     * 根据positionId查询position信息
     * @param positionId
     * @return
     */
    PositionEntity queryPositionById(String positionId);
}
