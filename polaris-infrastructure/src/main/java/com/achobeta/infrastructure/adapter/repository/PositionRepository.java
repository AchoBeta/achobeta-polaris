package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.team.adapter.repository.IPositionRepository;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.po.PositionPO;
import com.achobeta.infrastructure.redis.RedissonService;
import com.achobeta.types.common.Constants;
import com.achobeta.types.exception.AppException;
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

    /**
     * 查询某职位/分组下一级的所有职位/分组
     * @param positionId 需要查询子职位/分组的ID
     * @param teamId 需要查询子职位/分组的所属团队ID
     * @return 下级职位/分组列表
     * @date 2024/11/7
     */
    @Override
    public List<PositionEntity> querySubordinatePosition(String positionId, String teamId) {
        // 从缓存中获取数据
        List<PositionEntity> positionEntityList = redissonService.
                getValue(Constants.RedisKeyPrefix.TEAM_STRUCTURE_SUBORDINATE + positionId);
        if (positionEntityList!= null && !positionEntityList.isEmpty()) {
            return positionEntityList;
        }

        // 验证团队是否存在
        PositionPO team = positionMapper.getPositionByPositionId(teamId);
        if (team == null) {
            log.error("查询团队组织架构失败，找不到团队信息，positionId:{}, teamId:{}", positionId, teamId);
            throw new AppException(Constants.ResponseCode.TEAM_NOT_EXIST.getCode(),
                    Constants.ResponseCode.TEAM_NOT_EXIST.getInfo());
        }

        // 从数据库中查询下级职位/分组
        List<PositionPO> positionPOList = positionMapper.
                listSubordinateByPositionIdAndTeamId(positionId, teamId);
        positionEntityList = new ArrayList<>();
        for (PositionPO positionPO : positionPOList) {
            positionEntityList.add(PositionEntity.builder()
                    .positionId(positionPO.getPositionId())
                    .positionName(positionPO.getPositionName())
                    .teamId(positionPO.getTeamId())
                    .build());
        }

        // 缓存数据
        redissonService.setValue(Constants.RedisKeyPrefix.TEAM_STRUCTURE_SUBORDINATE + positionId, positionEntityList);
        return positionEntityList;
    }

}
