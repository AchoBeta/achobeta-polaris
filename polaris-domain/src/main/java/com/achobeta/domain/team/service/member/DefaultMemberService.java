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
import java.util.stream.Collectors;


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
                      String userId = teamBO.getUserId();
                        String memberId = teamBO.getUserEntity().getUserId();
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

    @Override
    public UserEntity addMember(UserEntity userEntity, String userId, String teamId, List<List<String>> positions) {
        PostContext<TeamBO> postContext = buildPostContext(userEntity, userId, teamId, positions);
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
                        memberRepository.addMember(userEntity, teamBO.getUserId(), teamBO.getTeamId(), (List<List<String>>)postContext.getExtraData("addPositions"));
                      return postContext;
                    }
                });
        return postContext.getBizData().getUserEntity();
    }

    @Override
    public UserEntity modifyMember(String userId, String teamId, UserEntity userEntity, List<List<String>> addPositions, List<List<String>> deletePositions) {
        PostContext<TeamBO> postContext = buildPostContext(teamId, userEntity);
        postContext.addExtraData("addPositions", addPositions);
        postContext.addExtraData("deletePositions", deletePositions);
        postContext.addExtraData("userId", userId);
        postContext = super.doPostProcessor(postContext, ModifyMemberInfoPostProcessor.class,
                new AbstractPostProcessorOperation<TeamBO>() {
                    @Override
                    public PostContext<TeamBO> doMainProcessor(PostContext<TeamBO> postContext) {
                        TeamBO teamBO = postContext.getBizData();
                        String teamId = teamBO.getTeamId();
                        UserEntity userEntity = teamBO.getUserEntity();
                        log.info("访问修改团队成员功能，开始处理，teamId: {}, userId: {}",teamId, userEntity.getUserId());

                        // 过滤掉元素不到2个的，即没有同时包含团队和对应职位的
                        memberRepository.modifyMemberInfo(userEntity, teamId,
                                ((List<List<String>>)postContext.getExtraData("addPositions")).stream()
                                        .filter(list -> list.size() >= 2)
                                        .collect(Collectors.toList()),
                                ((List<List<String>>)postContext.getExtraData("deletePositions")).stream()
                                        .filter(list -> list.size() >= 2)
                                        .collect(Collectors.toList()),
                                (String)postContext.getExtraData("userId"));

                        log.info("访问修改团队成员功能，处理结束，teamId: {}, userId: {}",teamId, userEntity.getUserId());
                        return postContext;
                    }
                });
        return postContext.getBizData().getUserEntity();
    }

    @Override
    public UserEntity queryMemberInfo(String userId, String memberId) {
        PostContext<TeamBO> postContext = buildPostContext(memberId);
        postContext = super.doPostProcessor(postContext, AddMemberPostProcessor.class,
                new AbstractPostProcessorOperation<TeamBO>() {
                    @Override
                    public PostContext<TeamBO> doMainProcessor(PostContext<TeamBO> postContext) {
                        TeamBO teamBO = postContext.getBizData();
                        log.info("开始查询团队成员信息，userId:{}，memberId:{}, teamId:{}",
                                teamBO.getUserId(),
                                teamBO.getUserEntity().getUserId(),
                                teamBO.getTeamId());

                        UserEntity userEntity = memberRepository.queryMemberInfo(teamBO.getUserId(), teamBO.getUserEntity().getUserId());

                        log.info("查询团队成员信息成功，userId:{}，memberId:{}, teamId:{}",
                                teamBO.getUserId(),
                                teamBO.getUserEntity().getUserId(),
                                teamBO.getTeamId());
                        postContext.setBizData(TeamBO.builder().userEntity(userEntity).build());
                        return postContext;
                    }
                });
        return postContext.getBizData().getUserEntity();
    }

    private static PostContext<TeamBO> buildPostContext(UserEntity userEntity, String userId, String teamId, List<List<String>> positions) {
        PostContext<TeamBO> postContext = PostContext.<TeamBO>builder()
                .bizId(BizModule.TEAM.getCode())
                .bizName(BizModule.TEAM.getName())
                .bizData(TeamBO.builder()
                        .userEntity(userEntity)
                        .userId(userId)
                        .teamId(teamId)
                        .build())
                .build();

        postContext.addExtraData("positions", positions);

        return postContext;
    }

    private static PostContext<TeamBO> buildPostContext(String teamId, UserEntity userEntity) {
        return PostContext.<TeamBO>builder()
                .bizName(BizModule.TEAM.getName())
                .bizId(BizModule.TEAM.getCode())
                .bizData(TeamBO.builder().teamId(teamId).userEntity(userEntity).build())
                .build();
    }

    private static PostContext<TeamBO> buildPostContext(String memberId) {
        return PostContext.<TeamBO>builder()
                .bizName(BizModule.TEAM.getName())
                .bizData(TeamBO.builder()
                        .userEntity(UserEntity.builder().userId(memberId).build())
                        .build())
                .build();
    }

    @Override
    public List<UserEntity> queryMembers(String teamId, String lastId, Integer limit) {
        PostContext<TeamBO> postContext = buildPostContext(teamId, lastId, limit);
        postContext = super.doPostProcessor(postContext, MemberListPostProcessor.class,
                new AbstractPostProcessorOperation<TeamBO>() {
                    @Override
                    public PostContext<TeamBO> doMainProcessor(PostContext<TeamBO> postContext) {
                        String teamId = postContext.getBizData().getTeamId();
                        String lastId = (String) postContext.getExtraData("lastId");
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

    private static PostContext<TeamBO> buildPostContext(String teamId, String lastId, Integer limit) {
        PostContext<TeamBO> postContext = PostContext.<TeamBO>builder()
               .bizName(BizModule.TEAM.getName())
               .bizData(TeamBO.builder().teamId(teamId).build())
               .build();
        postContext.addExtraData("lastId", lastId);
        postContext.addExtraData("limit", limit);
        return postContext;
    }

}
