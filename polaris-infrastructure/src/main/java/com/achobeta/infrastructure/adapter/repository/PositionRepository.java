package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.render.model.entity.PositionEntity;
import com.achobeta.domain.team.adapter.repository.IPositionRepository;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.po.PositionPO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 职位仓储接口实现类
 * @date 2024/11/7
 */
@Repository
public class PositionRepository implements IPositionRepository {

    @Resource
    private PositionMapper positionMapper;

    @Override
    public List<PositionEntity> querySubordinatePosition(String positionId) {
        List<PositionPO> positionPOList = positionMapper.listSubordinateByPositionId(positionId);

        List<PositionEntity> positionEntityList = new ArrayList<>();
        for (PositionPO positionPO : positionPOList) {
            positionEntityList.add(PositionEntity.builder()
                    .positionId(positionPO.getPositionId())
                    .positionName(positionPO.getPositionName())
                    .level(positionPO.getLevel())
                    .subordinate(positionPO.getSubordinate())
                    .build());
        }

        return positionEntityList;
    }

}
