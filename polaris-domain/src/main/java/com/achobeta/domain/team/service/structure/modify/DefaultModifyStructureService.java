package com.achobeta.domain.team.service.structure.modify;

import cn.hutool.core.collection.CollectionUtil;
import com.achobeta.domain.team.adapter.repository.IPositionRepository;
import com.achobeta.domain.team.model.bo.TeamBO;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.domain.team.service.IModifyStructureService;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yangzhiyao
 * @description 修改团队组织架构的默认实现
 * @date 2024/11/11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultModifyStructureService extends AbstractPostProcessor<TeamBO> implements IModifyStructureService {

    private final IPositionRepository repository;


    @Override
    public List<PositionEntity> modifyStructure(List<PositionEntity> newPositionList, List<PositionEntity> deletePositionList, String teamId) {
        PostContext<TeamBO> postContext = buildPostContext(newPositionList, deletePositionList, teamId);
        postContext = super.doPostProcessor(postContext, ModifyStructurePostProcessor.class);
        return postContext.getBizData().getPositionEntityList();
    }

    @Override
    public PostContext<TeamBO> doMainProcessor(PostContext<TeamBO> postContext) {
        TeamBO teamBO = postContext.getBizData();
        List<PositionEntity> newPositionList = teamBO.getPositionEntityList();
        List<PositionEntity> positionsToDelete = (List<PositionEntity>) postContext.getExtraData("positionsToDelete");
        String teamId = (String) postContext.getExtraData("teamId");

        // 需要新增的所有节点，包括固有节点
        List<PositionEntity> positionsToAdd = new ArrayList<>();
        // 有需要新增的职位/分组
        if (!CollectionUtil.isEmpty(newPositionList)) {
            // 哈希表，key-name，value-uuid
            ConcurrentHashMap<String, String> positionMap = new ConcurrentHashMap<>(newPositionList.size());
            for (PositionEntity positionEntity : newPositionList) {
                String newId = UUID.randomUUID().toString();
                // 为新职位创建固有节点
                PositionEntity newPositionEntity = PositionEntity.builder()
                        .positionId(newId)
                        .positionName(positionEntity.getSubordinateName())
                        .teamId(teamId)
                        .subordinateId("")
                        .level((byte) (positionEntity.getLevel() + 1))
                        .build();
                positionMap.put(positionEntity.getSubordinateName(), newId);
                // 看父节点，按照逻辑父节点要么是之前创建的有id，要么是新创建的
                // 如果新创建的，在遍历过程总会先生成UUID放入哈希表，所以这里如果没有那就是非法请求
                String positionId = positionEntity.getPositionId();
                if (positionId == null || positionId.isEmpty()) {
                    positionId = positionMap.get(positionEntity.getPositionName());
                    if (positionId == null) {
                        throw new AppException(String.valueOf(GlobalServiceStatusCode.TEAM_STRUCTURE_ADD_INVALID.getCode()),
                                GlobalServiceStatusCode.TEAM_STRUCTURE_ADD_INVALID.getMessage());
                    } else {
                        positionEntity.setPositionId(positionId);
                    }
                }
                positionEntity.setSubordinateId(positionMap.get(positionEntity.getSubordinateName()));
                positionEntity.setTeamId(teamId);

                positionsToAdd.add(newPositionEntity);
            }
            positionsToAdd.addAll(newPositionList);
            repository.savePosition(positionsToAdd, teamId);
        }

        // 删除职位/分组
        if (!CollectionUtil.isEmpty(positionsToDelete)) {
            repository.deletePosition(positionsToDelete, teamId);
        }

        postContext.setBizData(TeamBO.builder().positionEntityList(positionsToAdd).build());
        return postContext;
    }

    private static PostContext<TeamBO> buildPostContext(List<PositionEntity> newPositionList, List<PositionEntity> deletePositionList, String teamId) {
        PostContext<TeamBO> postContext = PostContext.<TeamBO>builder()
                .bizName(BizModule.TEAM.getName())
                .bizId(BizModule.TEAM.getCode())
                .bizData(TeamBO.builder().positionEntityList(newPositionList).build())
                .build();
        postContext.addExtraData("positionsToDelete", deletePositionList);
        postContext.addExtraData("teamId", teamId);
        return postContext;
    }

}
