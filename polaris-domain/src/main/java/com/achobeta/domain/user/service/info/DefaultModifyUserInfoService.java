package com.achobeta.domain.user.service.info;

import com.achobeta.domain.user.adapter.repository.IUserRepository;
import com.achobeta.domain.user.model.bo.UserBO;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.domain.user.service.IModifyUserInfoService;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.support.postprocessor.AbstractFunctionPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yangzhiyao
 * @description 用户修改个人信息服务默认实现
 * @date 2024/11/9
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultModifyUserInfoService extends AbstractFunctionPostProcessor<UserBO> implements IModifyUserInfoService {

    private final IUserRepository repository;

    @Override
    public void modifyUserInfo(UserEntity userEntity) {
        PostContext<UserBO> postContext = buildPostContext(userEntity);
        super.doPostProcessor(postContext, ModifyUserInfoPostProcessor.class,
                new AbstractPostProcessorOperation<UserBO>() {
                    @Override
                    public PostContext<UserBO> doMainProcessor(PostContext<UserBO> postContext) {
                        UserBO userBO = postContext.getBizData();
                        UserEntity userEntity = userBO.getUserEntity();

                        log.info("开始修改用户信息，userId:{}", userEntity.getUserId());
                        repository.updateUserInfo(userEntity);
                        log.info("用户信息修改结束，userId:{}", userEntity.getUserId());

                        postContext.setBizData(UserBO.builder().userEntity(userEntity).build());
                        return postContext;
                    }
                });
    }

    private static PostContext<UserBO> buildPostContext(UserEntity userEntity) {
        return PostContext.<UserBO>builder()
                .bizName(BizModule.USER.getName())
                .bizData(UserBO.builder()
                        .userEntity(userEntity)
                        .build())
                .build();
    }
}
