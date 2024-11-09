package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.user.adapter.repository.IUserRepository;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.dao.po.UserPO;
import com.achobeta.infrastructure.redis.IRedisService;
import com.achobeta.types.common.Constants;
import com.achobeta.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author yangzhiyao
 * @description 用户仓储接口实现类
 * @date 2024/11/6
 */
@Slf4j
@Repository
public class UserRepository implements IUserRepository {

    @Resource
    private UserMapper userMapper;

    @Resource
    private IRedisService redisService;

    @Override
    public UserEntity getUserInfoInCache(String userId) {
        return redisService.getValue("user_info_" + userId);
    }

    @Override
    public void setUserInfoInCache(String userId, UserEntity userEntity) {
        redisService.setValue("user_info_" + userId, userEntity);
    }

    @Override
    public void removeUserInfoInCache(String userId) {
        redisService.remove("user_info_" + userId);
    }

    @Override
    public UserEntity queryUserInfo(String userId) {
        UserPO userPO = userMapper.getUserByUserId(userId);
        if(userPO == null) {
            log.error("用户不存在！userId：{}",userId);
            throw new AppException(Constants.ResponseCode.USER_NOT_EXIST.getCode(),
                    Constants.ResponseCode.USER_NOT_EXIST.getInfo());
        }

        return UserEntity.builder()
                    .userId(userPO.getUserId())
                    .userName(userPO.getUserName())
                    .phone(userPO.getPhone())
                    .gender(userPO.getGender())
                    .idCard(userPO.getIdCard())
                    .email(userPO.getEmail())
                    .grade(userPO.getGrade())
                    .major(userPO.getMajor())
                    .studentId(userPO.getStudentId())
                    .experience(userPO.getExperience())
                    .currentStatus(userPO.getCurrentStatus())
                    .entryTime(userPO.getEntryTime())
                    .likeCount(userPO.getLikeCount())
                .build();
    }
}
