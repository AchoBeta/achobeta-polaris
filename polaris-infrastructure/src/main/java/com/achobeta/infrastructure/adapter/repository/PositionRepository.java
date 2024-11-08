package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.render.model.entity.PositionEntity;
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
     * @param positionId
     * @return 下级职位/分组列表
     * @date 2024/11/7
     */
    @Override
    public List<PositionEntity> querySubordinatePosition(String positionId) {
        log.info("repository querySubordinatePosition start，positionId: {}", positionId);

        log.info("queryTeams positionMapper.listSubordinateByPositionId(positionId) start: {}", positionId);
        List<PositionPO> positionPOList = positionMapper.listSubordinateByPositionId(positionId);
        if(positionPOList == null || positionPOList.isEmpty()) {
            return null;
        }
        log.info("queryTeams positionMapper.listSubordinateByPositionId(positionId) over: {}", positionId);

        List<PositionEntity> positionEntityList = new ArrayList<>();
        for (PositionPO positionPO : positionPOList) {
            positionEntityList.add(PositionEntity.builder()
                    .positionId(positionPO.getPositionId())
                    .positionName(positionPO.getPositionName())
                    .level(positionPO.getLevel())
                    .build());
        }

        log.info("repository querySubordinatePosition over，positionId: {}", positionId);
        return positionEntityList;
    }

}
