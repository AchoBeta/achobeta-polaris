package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.PositionPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 职位/分组dao
 * @date 2024/11/7
 */
@Mapper
public interface PositionMapper {

    List<PositionPO> listSubordinateByPositionId(String positionName, String teamName);

}
