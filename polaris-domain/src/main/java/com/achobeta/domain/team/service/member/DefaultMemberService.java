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
import java.util.UUID;

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
    public UserEntity addMember(UserEntity userEntity, String userId, String teamId, List<String> positionIds) {
        PostContext<TeamBO> postContext = buildPostContext(userEntity, userId, teamId, positionIds);
        postContext = super.doPostProcessor(postContext, AddMemberPostProcessor.class,
                new AbstractPostProcessorOperation<TeamBO>() {
                    @Override
                    public PostContext<TeamBO> doMainProcessor(PostContext<TeamBO> postContext) {
                        TeamBO teamBO = postContext.getBizData();
                        UserEntity userEntity = teamBO.getUserEntity();

                        log.info("判断手机号所属用户是否已存在，phone:{}, teamId:{}",userEntity.getPhone(), teamId);
                        UserEntity user = memberRepository.queryMemberByPhone(userEntity.getPhone());
                        if (user!= null) {
                            log.warn("手机号所属用户已存在，phone:{}, teamId:{}",userEntity.getPhone(), teamId);
                            postContext.setBizData(TeamBO.builder().userEntity(user).build());
                            return postContext;
                        }

                        userEntity.setUserId(UUID.randomUUID().toString());
                        memberRepository.addMember(userEntity, teamBO.getUserId(), teamBO.getTeamId(), teamBO.getPositionIds());
                        return postContext;
                    }
                });
        return postContext.getBizData().getUserEntity();
    }

    private static PostContext<TeamBO> buildPostContext(UserEntity userEntity, String userId, String teamId, List<String> positionIds) {
        return PostContext.<TeamBO>builder()
                .bizId(BizModule.TEAM.getCode())
                .bizName(BizModule.TEAM.getName())
                .bizData(TeamBO.builder()
                        .userEntity(userEntity)
                        .userId(userId)
                        .teamId(teamId)
                        .positionIds(positionIds)
                        .build())
                .build();
    }

}
