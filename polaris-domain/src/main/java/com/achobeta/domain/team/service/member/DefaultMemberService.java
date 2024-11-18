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
    public void addMember(UserEntity userEntity, String userId, String teamId, List<String> positionIds) {
        PostContext<TeamBO> postContext = buildPostContext(userEntity, userId, teamId, positionIds);

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
