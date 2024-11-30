package com.achobeta.domain.auth.service.role;

import com.achobeta.domain.auth.adapter.repository.IAuthRepository;
import com.achobeta.domain.auth.model.bo.AuthBO;
import com.achobeta.domain.auth.model.entity.RoleEntity;
import com.achobeta.domain.auth.service.IRoleService;
import com.achobeta.domain.team.model.entity.TeamEntity;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.support.postprocessor.AbstractFunctionPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 团队角色服务默认实现
 * @date 2024/11/27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultRoleService extends AbstractFunctionPostProcessor<AuthBO> implements IRoleService {

    private final IAuthRepository authRepository;

    @Override
    public List<RoleEntity> queryRoles(String userId) {
        PostContext<AuthBO> postContext = buildPostContext(userId);
        super.doPostProcessor(postContext, QueryRolesPostProcessor.class,
                new AbstractPostProcessorOperation<AuthBO>() {
                    @Override
                    public PostContext<AuthBO> doMainProcessor(PostContext<AuthBO> postContext) {
                        String userId = postContext.getBizData().getUserId();
                        log.info("开始查询可以支配赋予他人的团队及角色: {}", userId);
                        List<TeamEntity> teamEntityList = authRepository.queryRoles(userId);

                        List<RoleEntity> roleEntityList = new ArrayList<>();
                        for (TeamEntity teamEntity : teamEntityList) {
                            roleEntityList.addAll(teamEntity.getRoles());
                        }
                        postContext.getBizData().setRoleEntityList(roleEntityList);
                        postContext.getBizData().setTeamEntityList(teamEntityList);

                        return postContext;
                    }
                });
        return postContext.getBizData().getRoleEntityList();
    }

    private static PostContext<AuthBO> buildPostContext(String userId) {
        return PostContext.<AuthBO>builder()
                .bizId(BizModule.TEAM.getCode())
                .bizName(BizModule.TEAM.getName())
                .bizData(AuthBO.builder()
                        .userId(userId)
                        .build())
                .build();
    }
}
