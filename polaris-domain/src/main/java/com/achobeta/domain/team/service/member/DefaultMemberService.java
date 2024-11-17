package com.achobeta.domain.team.service.member;

import com.achobeta.domain.team.adapter.repository.IMemberRepository;
import com.achobeta.domain.team.model.bo.TeamBO;
import com.achobeta.domain.team.service.IMemberService;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.support.postprocessor.AbstractFunctionPostProcessor;
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
public class DefaultMemberService extends AbstractFunctionPostProcessor<TeamBO> implements IMemberService {

    private final IMemberRepository memberRepository;

    @Override
    public UserEntity modifyMember(String teamId, UserEntity userEntity, List<String> addPositions, List<String> deletePositions) {
        PostContext<TeamBO> postContext = buildPostContext(teamId, userEntity);
        postContext.addExtraData("addPositions", addPositions);
        postContext.addExtraData("deletePositions", deletePositions);
        postContext = super.doPostProcessor(postContext, ModifyMemberInfoPostProcessor.class,
                new AbstractPostProcessorOperation<TeamBO>() {
                    @Override
                    public PostContext<TeamBO> doMainProcessor(PostContext<TeamBO> postContext) {
                        TeamBO teamBO = postContext.getBizData();
                        String teamId = teamBO.getTeamId();
                        UserEntity userEntity = teamBO.getUserEntity();
                        log.info("访问修改团队成员功能，开始处理，teamId: {}, userId: {}",teamId, userEntity.getUserId());

                        memberRepository.modifyMemberInfo(userEntity, teamId,
                                (List<String>)postContext.getExtraData("addPositions"),
                                (List<String>)postContext.getExtraData("deletePositions"));

                        log.info("访问修改团队成员功能，处理结束，teamId: {}, userId: {}",teamId, userEntity.getUserId());

                        return postContext;
                    }
                });
        return postContext.getBizData().getUserEntity();
    }

    private static PostContext<TeamBO> buildPostContext(String teamId, UserEntity userEntity) {
        return PostContext.<TeamBO>builder()
                .bizName(BizModule.TEAM.getName())
                .bizId(BizModule.TEAM.getCode())
                .bizData(TeamBO.builder().teamId(teamId).userEntity(userEntity).build())
                .build();
    }
}
