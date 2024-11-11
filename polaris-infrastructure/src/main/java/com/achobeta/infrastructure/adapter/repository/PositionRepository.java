package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.domain.team.adapter.repository.IPositionRepository;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.po.PositionPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 职位仓储接口实现类
 * @create 2024/11/7
 */
@Slf4j
@Repository
public class PositionRepository implements IPositionRepository {

    @Resource
    private PositionMapper positionMapper;

    /**
     * 查询某职位/分组下一级的所有职位/分组
     * @param positionId 需要查询子职位/分组的ID
     * @param teamId 需要查询子职位/分组的所属团队ID
     * @return 下级职位/分组列表
     * @date 2024/11/7
     */
    @Override
    public List<PositionEntity> querySubordinatePosition(String positionId, String teamId) {
        List<PositionPO> positionPOList = positionMapper.listSubordinateByPositionIdAndTeamId(positionId, teamId);
        List<PositionEntity> positionEntityList = new ArrayList<>();
        for (PositionPO positionPO : positionPOList) {
            positionEntityList.add(PositionEntity.builder()
                    .positionId(positionPO.getPositionId())
                    .positionName(positionPO.getPositionName())
                    .teamId(positionPO.getTeamId())
                    .build());
        }
        return positionEntityList;
    }

}
