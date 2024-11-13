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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
            /* 按照职位等级从一级到四级排序，避免重复的遍历节点
             * 这样也省去了维护某用户要归并到的position的额外工作
             */
            List<PositionEntity> rootPositionsToDelete = positionsToDelete.stream()
                    .sorted(Comparator.comparingInt(PositionEntity::getLevel))
                    .collect(Collectors.toList());
            // 记录遍历到的所有节点
            HashSet<String> positionsToDeleteSet = new HashSet<>();
            for (PositionEntity positionEntity : rootPositionsToDelete) {
                if (!positionsToDeleteSet.contains(positionEntity.getPositionId())) {
                    // 这里的newPositionToBind是因为职位/分组被删除后，需要重新绑定到的父节点
                    String newPositionToBind = repository
                            .queryParentPosition(positionEntity.getPositionId())
                            .getPositionId();
                    Queue<PositionEntity> queue = new LinkedList<>();
                    queue.add(positionEntity);
                    while(!queue.isEmpty()) {
                        PositionEntity tempPosition = queue.poll();
                        positionsToDeleteSet.add(tempPosition.getPositionId());
                        List<PositionEntity> subordinates = repository.querySubordinatePosition(tempPosition.getPositionId(), teamId);
                        tempPosition.setSubordinates(subordinates);
                        if(!CollectionUtil.isEmpty(subordinates)) {
                            queue.addAll(subordinates);
                        }
                    }
                    if (!CollectionUtil.isEmpty(positionsToDeleteSet)) {
                        // 查找因为职位/分组被删除需要重新绑定父节点的用户
                        List<String> userIdsToRebind = repository.
                                queryUserIdsByPositionIds(positionsToDeleteSet);
                        if (!CollectionUtil.isEmpty(userIdsToRebind)) {
                            repository.bindUsersToPosition(newPositionToBind, userIdsToRebind);
                        }
                    }
                }
            }
            repository.deletePosition(positionsToDeleteSet, teamId);
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
