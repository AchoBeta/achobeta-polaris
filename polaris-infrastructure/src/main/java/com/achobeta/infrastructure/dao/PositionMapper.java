package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.PositionPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yangzhiyao
 * @description 职位/分组dao
 * @date 2024/11/7
 */
@Mapper
public interface PositionMapper {

    PositionPO getPositionByPositionId(String positionId);

}
