package com.achobeta.domain.team.adapter.port;

import com.achobeta.domain.team.adapter.repository.IPositionRepository;
import com.achobeta.domain.team.model.entity.PositionEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author yuangzhiyao
 * @description 查询用户团队信息的外部接口
 * @date 2024/11/23
 */
@RequiredArgsConstructor
public class TeamInfoPort {

    private final IPositionRepository positionRepository;

    public List<PositionEntity> queryTeamByUserId(String userId) {
        return positionRepository.queryTeamByUserId(userId);
    }
}
