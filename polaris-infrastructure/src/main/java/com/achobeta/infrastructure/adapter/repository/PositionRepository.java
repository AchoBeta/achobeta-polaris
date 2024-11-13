package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.team.adapter.repository.IPositionRepository;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.po.PositionPO;
import com.achobeta.infrastructure.redis.RedissonService;
import com.achobeta.types.common.Constants;
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

    @Resource
    private RedissonService redissonService;

    @Override
    public Boolean isTeamExists(String teamId) {
        return positionMapper.getPositionByPositionId(teamId) != null;
    }

    @Override
    public void savePosition(List<PositionEntity> positionEntityList, String teamId) {

        // 封装到PositionPO对象中
        List<PositionPO> positionPOList = new ArrayList<>();
        for (PositionEntity positionEntity : positionEntityList) {
            PositionPO positionPO = PositionPO.builder()
                            .positionId(positionEntity.getPositionId())
                            .positionName(positionEntity.getPositionName())
                            .subordinate(positionEntity.getSubordinateId())
                            .teamId(positionEntity.getTeamId())
                            .level(positionEntity.getLevel())
                            .build();
            positionPOList.add(positionPO);
        }

        // 保存到数据库中
        positionMapper.addPosition(positionPOList);

        // 清除缓存
        redissonService.remove(Constants.TEAM_STRUCTURE_SUBORDINATE + teamId + ":*");
    }

    @Override
    public void deletePosition(List<PositionEntity> positionsToDelete, String teamId) {

    }
}
