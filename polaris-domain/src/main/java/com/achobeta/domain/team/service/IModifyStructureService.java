package com.achobeta.domain.team.service;

import com.achobeta.domain.team.model.entity.PositionEntity;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 修改团队组织架构接口
 * @date 2024/11/11
 */
public interface IModifyStructureService {

    /**
     * 修改团队组织架构
     * @author yangzhiyao
     * @date 2024/11/13
     * @param newPositionList
     * @param positionsToDelete
     * @param teamId
     * @return
     */
    List<PositionEntity> modifyStructure(List<PositionEntity> newPositionList, List<PositionEntity> positionsToDelete, String teamId);

}
