package com.achobeta.domain.user.service.info;

import com.achobeta.domain.user.adapter.repository.IUserRepository;
import com.achobeta.domain.user.model.bo.UserBO;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.domain.user.model.valobj.UserInfoVO;
import com.achobeta.domain.user.service.IUserInfoService;
import com.achobeta.types.common.Constants;
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
    public UserInfoVO getUserInfo(String userId) {
        PostContext<UserBO> userContext = buildPostContext(userId);
        userContext = super.doPostProcessor(userContext, UserInfoPostProcessor.class);
        return UserInfoVO.builder()
                .userId(userContext.getBizData().getUserEntity().getUserId())
                .userName(userContext.getBizData().getUserEntity().getUserName())
                .email(userContext.getBizData().getUserEntity().getEmail())
                .phone(userContext.getBizData().getUserEntity().getPhone())
                .grade(userContext.getBizData().getUserEntity().getGrade())
                .gender(userContext.getBizData().getUserEntity().getGender())
                .major(userContext.getBizData().getUserEntity().getMajor())
                .currentStatus(userContext.getBizData().getUserEntity().getCurrentStatus())
                .entryTime(userContext.getBizData().getUserEntity().getEntryTime())
                .experience(userContext.getBizData().getUserEntity().getExperience())
                .idCard(userContext.getBizData().getUserEntity().getIdCard())
                .likeCount(userContext.getBizData().getUserEntity().getLikeCount())
                .studentId(userContext.getBizData().getUserEntity().getStudentId())
                .build();
    }

    @Override
    public PostContext<UserBO> doMainProcessor(PostContext<UserBO> postContext) {
        UserBO userBO = postContext.getBizData();

        UserEntity userEntity = repository.queryUserInfo(userBO.getUserEntity().getUserId());

        postContext.setBizData(UserBO.builder().userEntity(userEntity).build());
        return postContext;
    }

    private static PostContext<UserBO> buildPostContext(String userId) {
        return PostContext.<UserBO>builder()
                .bizName(Constants.BizModule.USER.getName())
                .bizData(UserBO.builder()
                        .userEntity(UserEntity.builder().userId(userId).build())
                        .build())
                .build();
    }
}
