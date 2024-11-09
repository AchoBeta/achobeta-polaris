package com.achobeta.domain.user.service.modifyInfo;

import com.achobeta.domain.user.adapter.repository.IUserRepository;
import com.achobeta.domain.user.model.bo.UserBO;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.domain.user.service.IModifyUserInfoService;
import com.achobeta.domain.user.service.info.UserInfoPostProcessor;
import com.achobeta.types.common.Constants;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author yangzhiyao
 * @description 用户修改个人信息服务默认实现
 * @date 2024/11/9
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultModifyUserInfoService extends AbstractPostProcessor<UserBO> implements IModifyUserInfoService {

    private final IUserRepository repository;

    @Override
    public void modifyUserInfo(String userId, String userName, String phone,
                               Byte gender, String idCard, String email, Integer grade,
                               String major, String studentId, String experience,
                               String currentStatus, LocalDateTime entryTime) {
        UserEntity userEntity = UserEntity.builder()
                                .userId(userId)
                                .userName(userName)
                                .phone(phone)
                                .gender(gender)
                                .idCard(idCard)
                                .email(email)
                                .grade(grade)
                                .major(major)
                                .studentId(studentId)
                                .experience(experience)
                                .currentStatus(currentStatus)
                                .entryTime(entryTime)
                                .build();
        PostContext<UserBO> postContext = buildPostContext(userEntity);
        postContext = super.doPostProcessor(postContext, UserInfoPostProcessor.class);
    }

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

    private static PostContext<UserBO> buildPostContext(UserEntity userEntity) {
        return PostContext.<UserBO>builder()
                .bizName(Constants.BizModule.USER.getName())
                .bizData(UserBO.builder()
                        .userEntity(userEntity)
                        .build())
                .build();
    }
}
