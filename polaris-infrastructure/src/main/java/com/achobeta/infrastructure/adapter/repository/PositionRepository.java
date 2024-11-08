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
     * @param positionName
     * @return 下级职位/分组列表
     * @date 2024/11/7
     */
    @Override
    public List<PositionEntity> querySubordinatePosition(String positionName, String teamName) {
        log.info("repository querySubordinatePosition start，positionName: {}，teamName: {}", positionName, teamName);

        log.info("queryTeams positionMapper.listSubordinateIdByPositionNameAndTeamName start: positionName: {}，teamName: {}", positionName, teamName);
        List<String> positionIdList = positionMapper.listSubordinateIdByPositionNameAndTeamName(positionName, teamName);
        log.info("queryTeams positionMapper.listSubordinateIdByPositionNameAndTeamName over: positionName: {}，teamName: {}", positionName, teamName);
        if(positionIdList == null || positionIdList.isEmpty()) {
            return null;
        }

        log.info("queryTeams positionMapper.listPositionByPositionId start: positionName: {}，teamName: {}", positionName, teamName);
        List<PositionPO> positionPOList = positionMapper.listPositionByPositionId(positionIdList);
        log.info("queryTeams positionMapper.listPositionByPositionId over: positionName: {}，teamName: {}", positionName, teamName);
        if(positionPOList == null || positionPOList.isEmpty()) {
            return null;
        }

        List<PositionEntity> positionEntityList = new ArrayList<>();
        for (PositionPO positionPO : positionPOList) {
            positionEntityList.add(PositionEntity.builder()
                    .positionId(positionPO.getPositionId())
                    .positionName(positionPO.getPositionName())
                    .teamName(positionPO.getTeamName())
                    .level(positionPO.getLevel())
                    .build());
        }

        log.info("repository querySubordinatePosition over，positionName: {}，teamName: {}", positionName, teamName);
        return positionEntityList;
    }

}
