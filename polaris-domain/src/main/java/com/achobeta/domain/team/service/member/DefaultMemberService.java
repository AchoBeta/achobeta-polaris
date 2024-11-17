package com.achobeta.domain.team.service.member;

import com.achobeta.domain.team.model.bo.TeamBO;
import com.achobeta.domain.team.service.IMemberService;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 团队成员服务默认实现
 * @date 2024/11/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMemberService extends AbstractPostProcessor<TeamBO> implements IMemberService {

    @Override
    public UserEntity modifyMember(String teamId, UserEntity userEntity, List<String> addPositions, List<String> deletePositions) {
        PostContext<TeamBO> postContext = buildPostContext(teamId, userEntity);
        postContext.addExtraData("addPositions", addPositions);
        postContext.addExtraData("deletePositions", deletePositions);
        postContext = super.doPostProcessor(postContext, ModifyMemberInfoPostProcessor.class);
        ModifyMemberInfoPostProcessor processor = new ModifyMemberInfoPostProcessor();
        postContext = processor.doMainProcessor(postContext);
        return postContext.getBizData().getUserEntity();
    }

    private static PostContext<TeamBO> buildPostContext(String teamId, UserEntity userEntity) {
        return PostContext.<TeamBO>builder()
                .bizName(BizModule.TEAM.getName())
                .bizId(BizModule.TEAM.getCode())
                .bizData(TeamBO.builder().teamId(teamId).userEntity(userEntity).build())
                .build();
    }

    @Override
    public PostContext<TeamBO> doMainProcessor(PostContext<TeamBO> postContext) {
        return postContext;
    }
}
