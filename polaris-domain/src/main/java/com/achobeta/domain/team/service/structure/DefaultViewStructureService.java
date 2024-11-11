package com.achobeta.domain.team.service.structure;

import com.achobeta.domain.team.adapter.repository.IPositionRepository;
import com.achobeta.domain.team.model.bo.PositionBO;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.domain.team.service.IViewStructureService;
import com.achobeta.types.common.Constants;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author yangzhiyao
 * @description 查看团队组织架构的默认实现
 * @date 2024/11/8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultViewStructureService extends AbstractPostProcessor<PositionBO> implements IViewStructureService {

    private final IPositionRepository repository;

    @Override
    public PositionEntity queryStructure(String teamId) {
        if (!repository.isTeamExists(teamId)) {
            log.error("团队不存在！teamId：{}", teamId);
            throw new AppException(Constants.ResponseCode.TEAM_NOT_EXIST.getCode(),
                    Constants.ResponseCode.TEAM_NOT_EXIST.getInfo());
        }
        PostContext<PositionBO> postContext = buildPostContext(teamId);
        postContext = super.doPostProcessor(postContext, ViewStructurePostProcessor.class);
        return postContext.getBizData().getPositionEntity();
    }

    @Override
    public PostContext<PositionBO> doMainProcessor(PostContext<PositionBO> postContext) {
        PositionBO positionBO = postContext.getBizData();
        PositionEntity positionEntity = positionBO.getPositionEntity();

        positionEntity.setPositionId(positionEntity.getTeamId());
        Queue<PositionEntity> queue = new LinkedList<>();
        queue.add(positionEntity);
        while(!queue.isEmpty()) {
            PositionEntity tempPosition = queue.poll();
            List<PositionEntity> subordinates = repository.querySubordinatePosition(tempPosition.getPositionId());
            tempPosition.setSubordinates(subordinates);
            if(subordinates!= null && !subordinates.isEmpty()) {
                queue.addAll(subordinates);
            }
        }

        postContext.setBizData(PositionBO.builder().positionEntity(positionEntity).build());
        return postContext;
    }

    private static PostContext<PositionBO> buildPostContext(String teamId) {
        return PostContext.<PositionBO>builder()
                .bizName(Constants.BizModule.TEAM.getName())
                .bizData(PositionBO.builder()
                        .positionEntity(PositionEntity.builder().teamId(teamId).build())
                        .build())
                .build();
    }

}
