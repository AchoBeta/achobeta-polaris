package com.achobeta.domain.team.service.structure;

import com.achobeta.domain.render.model.entity.PositionEntity;
import com.achobeta.domain.team.adapter.repository.IPositionRepository;
import com.achobeta.domain.team.model.bo.PositionBO;
import com.achobeta.domain.team.service.IViewStructureService;
import com.achobeta.types.common.Constants;
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
    public PositionEntity queryStructure(String teamName) {
        PostContext<PositionBO> postContext = buildPostContext(teamName);
        postContext = super.doPostProcessor(postContext, ViewStructurePostProcessor.class);
        return PositionEntity.builder()
                .positionId(postContext.getBizData().getPositionEntity().getPositionId())
                .positionName(postContext.getBizData().getPositionEntity().getPositionName())
                .level(postContext.getBizData().getPositionEntity().getLevel())
                .subordinates(postContext.getBizData().getPositionEntity().getSubordinates())
                .build();
    }

    @Override
    public PostContext<PositionBO> doMainProcessor(PostContext<PositionBO> postContext) {
        PositionBO positionBO = postContext.getBizData();
        PositionEntity positionEntity = positionBO.getPositionEntity();

        Queue<PositionEntity> queue = new LinkedList<>();
        queue.add(positionEntity);
        while(!queue.isEmpty()) {
            PositionEntity tempPosition = queue.poll();
            List<PositionEntity> subordinates = repository.querySubordinatePosition(tempPosition.getPositionName());
            tempPosition.setSubordinates(subordinates);
            if(subordinates!= null && !subordinates.isEmpty()) {
                queue.addAll(subordinates);
            }
        }

        postContext.setBizData(PositionBO.builder().positionEntity(positionEntity).build());
        return postContext;
    }

    private static PostContext<PositionBO> buildPostContext(String teamName) {
        return PostContext.<PositionBO>builder()
                .bizName(Constants.BizModule.TEAM.getName())
                .bizData(PositionBO.builder()
                        .positionEntity(PositionEntity.builder().teamName(teamName).build())
                        .build())
                .build();
    }

}
