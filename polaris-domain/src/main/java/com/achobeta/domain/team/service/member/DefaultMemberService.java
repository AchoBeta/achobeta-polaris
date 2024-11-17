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
    public List<UserEntity> queryMembers(String teamId, Long lastId, Integer limit) {
        PostContext<TeamBO> postContext = buildPostContext(teamId, lastId, limit);
        postContext = super.doPostProcessor(postContext, MemberPostProcessor.class,
                new AbstractPostProcessorOperation<TeamBO>() {
                    @Override
                    public PostContext<TeamBO> doMainProcessor(PostContext<TeamBO> postContext) {
                        String teamId = postContext.getBizData().getTeamId();
                        Long lastId = (Long) postContext.getExtraData("lastId");
                        lastId = lastId == null? 0 : lastId;
                        Integer limit = (Integer) postContext.getExtraData("limit");
                        log.info("开始查询团队成员列表，teamId:{}", teamId);

                        List<UserEntity> members = memberRepository.queryMemberList(teamId, lastId, limit);

                        log.info("查询团队成员列表结束，teamId:{}", teamId);
                        postContext.setBizData(TeamBO.builder().teamId(teamId).members(members).build());
                        return postContext;
                    }
                });
        return postContext.getBizData().getMembers();
    }

    private static PostContext<TeamBO> buildPostContext(String teamId, Long lastId, Integer limit) {
        PostContext<TeamBO> postContext = PostContext.<TeamBO>builder()
               .bizName(BizModule.TEAM.getName())
               .bizData(TeamBO.builder().teamId(teamId).build())
               .build();
        postContext.addExtraData("lastId", lastId);
        postContext.addExtraData("limit", limit);
        return postContext;
    }

}
