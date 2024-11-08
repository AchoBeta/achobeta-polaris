package com.achobeta.domain.team.service;

import com.achobeta.domain.team.model.entity.PositionEntity;

/**
 * @author yangzhiyao
 * @description 查询团队组织架构接口
 * @date 2024/11/7
 */
public interface IViewStructureService {

    PositionEntity queryStructure(String teamName);

}
