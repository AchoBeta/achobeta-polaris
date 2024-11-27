package com.achobeta.domain.login.service.login.extendbiz;

import com.achobeta.domain.login.adapter.repository.IUserRepository;
import com.achobeta.domain.login.model.bo.LoginBO;
import com.achobeta.domain.login.service.login.LoginPostProcessor;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 严豪哲
 * @Description: 用户校验
 * @Date: 2024/11/10 23:37
 * @Version: 1.0
 */

@Slf4j
@Component
public class VerifyTeamMemberPostProcessor implements LoginPostProcessor {
    @Resource
    private IUserRepository userRepository;

    @Override
    public boolean handleBefore(PostContext<LoginBO> postContext) {
        String phone = postContext.getBizData().getTokenVO().getPhone();
        log.info("正在验证手机号为{}的用户是否为团队成员",phone);
        UserEntity userEntity = userRepository.getUserByPhone(phone);
        if(null == userEntity){
            log.info("手机号为{}的用户不是团队成员，校验失败",phone);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getCode()), GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }else{
            log.info("手机号为{}的用户是团队成员，校验通过",phone);
            log.info("正在记录用户:{}的userId:{}",phone,userEntity.getUserId());
            postContext.getBizData().getTokenVO().setUserId(Long.valueOf(userEntity.getUserId()));
            return true;
        }
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE + 1;
    }
}
