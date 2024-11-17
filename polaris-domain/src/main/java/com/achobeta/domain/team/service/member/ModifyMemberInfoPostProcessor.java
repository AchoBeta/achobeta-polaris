package com.achobeta.domain.team.service.member;

import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.domain.team.adapter.repository.IMemberRepository;
import com.achobeta.domain.team.model.bo.TeamBO;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yangzhiyao
 * @description 查看团队成员处理器
 * @date 2024/11/17
 */
@Slf4j
@Component
public class ModifyMemberInfoPostProcessor extends AbstractPostProcessor<TeamBO> implements MemberPostProcessor {

    @Resource
    private IMemberRepository memberRepository;

    @Override
    public PostContext<TeamBO> doMainProcessor(PostContext<TeamBO> postContext) {
        TeamBO teamBO = postContext.getBizData();
        String teamId = teamBO.getTeamId();
        UserEntity userEntity = teamBO.getUserEntity();
        log.info("访问修改团队成员功能，开始处理，teamId: {}, userId: {}",teamId, userEntity.getUserId());

        memberRepository.modifyMemberInfo(userEntity, teamId);

        log.info("访问修改团队成员功能，处理结束，teamId: {}, userId: {}",teamId, userEntity.getUserId());

        return postContext;
    }
}
