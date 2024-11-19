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
    public UserEntity queryMemberInfo(String memberId) {
        PostContext<TeamBO> postContext = buildPostContext(memberId);
        postContext = super.doPostProcessor(postContext, AddMemberPostProcessor.class,
                new AbstractPostProcessorOperation<TeamBO>() {
                    @Override
                    public PostContext<TeamBO> doMainProcessor(PostContext<TeamBO> postContext) {
                        TeamBO userBO = postContext.getBizData();
                        log.info("开始查询团队成员信息，userId:{}，memberId:{}", userBO.getUserEntity().getUserId(), userBO.getUserId());

                        UserEntity userEntity = memberRepository.queryMemberInfo(userBO.getUserEntity().getUserId());

                        log.info("查询团队成员信息成功，userId:{}，memberId:{}",userBO.getUserEntity().getUserId(), userBO.getUserId());
                        postContext.setBizData(TeamBO.builder().userEntity(userEntity).build());
                        return postContext;
                    }
                });
        return postContext.getBizData().getUserEntity();
    }

    private static PostContext<TeamBO> buildPostContext(String memberId) {
        return PostContext.<TeamBO>builder()
                .bizName(BizModule.TEAM.getName())
                .bizData(TeamBO.builder()
                        .userEntity(UserEntity.builder().userId(memberId).build())
                        .build())
                .build();
    }
}
