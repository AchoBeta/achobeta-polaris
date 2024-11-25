package com.achobeta.infrastructure.adapter.port;

import cn.hutool.core.collection.CollectionUtil;
import com.achobeta.domain.login.adapter.port.ITeamInfoPort;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.po.PositionPO;
import lombok.RequiredArgsConstructor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yuangzhiyao
 * @description 查询用户团队信息的外部接口
 * @date 2024/11/23
 */
@RequiredArgsConstructor
public class TeamInfoPort implements ITeamInfoPort {

    @Resource
    private PositionMapper positionMapper;

    public List<PositionEntity> queryTeamByUserId(String userId) {
        List<PositionPO> positionPOList = positionMapper.listPositionByUserId(userId);
        if (CollectionUtil.isEmpty(positionPOList)) {
            return Collections.emptyList();
        }
        List<PositionEntity> positionEntityList = new ArrayList<>(positionPOList.size());
        for (PositionPO positionPO : positionPOList) {
            positionEntityList.add(PositionEntity.builder()
                    .positionId(positionPO.getPositionId())
                    .positionName(positionPO.getPositionName())
                    .level(positionPO.getLevel())
                    .build());
        }
        return positionEntityList;
    }
}
