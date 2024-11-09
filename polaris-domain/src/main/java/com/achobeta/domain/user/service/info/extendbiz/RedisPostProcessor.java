package com.achobeta.domain.user.service.info.extendbiz;

import com.achobeta.domain.user.adapter.repository.IUserRepository;
import com.achobeta.domain.user.model.bo.UserBO;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.domain.user.service.info.UserInfoPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yangzhiyao
 * @description 用户个人信息查询缓存
 * @date 2024/11/9
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisPostProcessor implements UserInfoPostProcessor {

    private final IUserRepository repository;

    @Override
    public boolean handleBefore(PostContext<UserBO> postContext) {
        UserBO userBO = postContext.getBizData();
        UserEntity userEntity = repository.getUserInfoInCache(userBO.getUserEntity().getUserId());
        return userEntity == null;
    }

    @Override
    public void handleAfter(PostContext<UserBO> postContext) {
        UserBO userBO = postContext.getBizData();
        UserEntity userEntity = userBO.getUserEntity();
        if (userEntity.getPhone() != null && !userEntity.getPhone().isEmpty()) {
            repository.setUserInfoInCache(userEntity.getUserId(), userEntity);
        }
    }

    @Override
    public int getPriority() {
        return -200;
    }

}
