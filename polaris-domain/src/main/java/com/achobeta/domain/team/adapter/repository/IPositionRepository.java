package com.achobeta.domain.team.adapter.repository;

import com.achobeta.domain.team.model.entity.PositionEntity;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 职位仓储接口
 * @date 2024/11/7
 */
public interface IPositionRepository {

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
    void deletePosition(List<PositionEntity> positionsToDelete, String teamId);

}
