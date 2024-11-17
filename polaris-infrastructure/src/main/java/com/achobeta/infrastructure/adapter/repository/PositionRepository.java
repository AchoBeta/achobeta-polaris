package com.achobeta.infrastructure.adapter.repository;

import cn.hutool.core.collection.CollectionUtil;
import com.achobeta.domain.team.adapter.repository.IPositionRepository;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.po.PositionPO;
import com.achobeta.infrastructure.redis.IRedisService;
import com.achobeta.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
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
    private IRedisService redisService;

    /**
     * 查询某职位/分组下一级的所有职位/分组
     * @param positionId 需要查询子职位/分组的ID
     * @return 下级职位/分组列表
     * @date 2024/11/7
     */
    @Override
    public List<PositionEntity> querySubordinatePosition(String positionId, String teamId) {
        // 从缓存中获取数据
        if (redisService.isExists(Constants.TEAM_STRUCTURE + teamId)) {
            List<PositionEntity> tempList = redisService.getFromMap(Constants.TEAM_STRUCTURE + teamId, positionId);
            if (!CollectionUtil.isEmpty(tempList)) {
                return tempList;
            }
        }

        // 从数据库中查询下级职位/分组
        List<PositionPO> positionPOList = positionMapper.
                listSubordinateByPositionId(positionId);
        List<PositionEntity> positionEntityList = new ArrayList<>();
        for (PositionPO positionPO : positionPOList) {
            positionEntityList.add(PositionEntity.builder()
                    .positionId(positionPO.getPositionId())
                    .positionName(positionPO.getPositionName())
                    .level(positionPO.getLevel())
                    .teamId(positionPO.getTeamId())
                    .build());
        }

        // 缓存数据
        redisService.addToMap(Constants.TEAM_STRUCTURE + teamId, positionId, positionEntityList);
        return positionEntityList;
    }

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

        redisService.remove(Constants.TEAM_STRUCTURE + teamId);
    }

    @Override
    public void deletePosition(Collection<String> positionsToDelete, String teamId) {
        // 数据库逻辑删除
        positionMapper.deletePositionInPosition(positionsToDelete);
        positionMapper.deletePositionInUserPosition(positionsToDelete);

        redisService.remove(Constants.TEAM_STRUCTURE + teamId);
    }

    @Override
    public PositionEntity queryParentPosition(String positionId) {
        PositionPO positionPO = positionMapper.getParentPositionByPositionId(positionId);
        if (positionPO == null) {
            return null;
        }
        return PositionEntity.builder()
               .positionId(positionPO.getPositionId())
               .positionName(positionPO.getPositionName())
               .level(positionPO.getLevel())
               .teamId(positionPO.getTeamId())
               .build();
    }

    @Override
    public List<String> queryUserIdsByPositionIds(Collection<String> positionIds) {
        return positionMapper.listUserIdsByPositionIds(positionIds);
    }

    @Override
    public void bindUsersToPosition(String positionId, List<String> userIds) {
        positionMapper.addUsersToPosition(positionId, userIds);
    }

}
