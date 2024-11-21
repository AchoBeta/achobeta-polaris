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
    public void deleteMember(String userId, String memberId, String teamId) {
        PostContext<TeamBO> postContext = buildPostContext(userId, memberId, teamId);
        super.doPostProcessor(postContext, DeleteMemberPostProcessor.class,
                new AbstractPostProcessorOperation<TeamBO>() {
                    @Override
                    public PostContext<TeamBO> doMainProcessor(PostContext<TeamBO> postContext) {
                        TeamBO teamBO = postContext.getBizData();
                        String userId = teamBO.getUserEntity().getUserId();
                        String memberId = teamBO.getUserId();
                        String teamId = teamBO.getTeamId();

                        log.info("删除成员信息, userId:{}, memberId:{}, teamId:{}", userId, memberId, teamId);
                        memberRepository.deleteMember(userId, memberId, teamId);

                        return postContext;
                    }
                });
    }

    private static PostContext<TeamBO> buildPostContext(String userId, String memberId, String teamId) {
        return PostContext.<TeamBO>builder()
                .bizId(BizModule.TEAM.getCode())
                .bizName(BizModule.TEAM.getName())
                .bizData(TeamBO.builder()
                        .userEntity(UserEntity.builder().userId(memberId).build())
                        .teamId(teamId)
                        .userId(userId)
                        .build())
                .build();
    }
}
