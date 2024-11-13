package com.achobeta.domain.user.service.info;

import com.achobeta.domain.user.adapter.repository.IUserRepository;
import com.achobeta.domain.user.model.bo.UserBO;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.domain.user.service.IUserInfoService;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yangzhiyao
 * @description 用户个人中心信息服务默认实现
 * @date 2024/11/6
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUserInfoService extends AbstractPostProcessor<UserBO> implements IUserInfoService {

    private final IUserRepository repository;


    @Override
    public UserEntity getUserInfo(String userId) {
        PostContext<UserBO> userContext = buildPostContext(userId);
        userContext = super.doPostProcessor(userContext, UserInfoPostProcessor.class);
        return userContext.getBizData().getUserEntity();
    }

    @Override
    public PostContext<UserBO> doMainProcessor(PostContext<UserBO> postContext) {
        UserBO userBO = postContext.getBizData();
        log.info("开始查询用户信息，userId:{}",userBO.getUserEntity().getUserId());

        UserEntity userEntity = repository.queryUserInfo(userBO.getUserEntity().getUserId());
        // TODO:待添加获取用户点赞状态，以及用户所属团队职位分组

        log.info("查询用户信息成功，userId:{}",userBO.getUserEntity().getUserId());
        postContext.setBizData(UserBO.builder().userEntity(userEntity).build());
        return postContext;
    }

    private static PostContext<UserBO> buildPostContext(String userId) {
        return PostContext.<UserBO>builder()
                .bizName(BizModule.USER.getName())
                .bizData(UserBO.builder()
                        .userEntity(UserEntity.builder().userId(userId).build())
                        .build())
                .build();
    }
}
