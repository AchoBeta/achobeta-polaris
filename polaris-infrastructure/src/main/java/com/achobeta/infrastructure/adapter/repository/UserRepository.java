package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.user.adapter.repository.IUserRepository;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.dao.po.UserPO;
import com.achobeta.infrastructure.redis.IRedisService;
import com.achobeta.types.common.Constants;
import com.achobeta.types.enums.GlobalServiceStatusCode;
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
    public UserEntity queryUserInfo(String userId) {
        log.info("尝试从redis中获取用户信息，userId: {}",userId);
        UserEntity userEntity = redisService.getValue(Constants.USER_INFO + userId);
        if(userEntity!= null) {
            return userEntity;
        }

        log.info("从数据库中查询用户信息，userId: {}",userId);
        UserPO userPO = userMapper.getUserByUserId(userId);
        if(userPO == null) {
            log.error("用户不存在！userId：{}",userId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getCode()),
                    GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        log.info("从数据库中查询用户信息成功，userId: {}",userId);
        userEntity = UserEntity.builder()
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

        log.info("将用户信息缓存到redis，userId: {}",userId);
        redisService.setValue(Constants.USER_INFO + userId,userEntity);
        log.info("将用户信息缓存到redis成功，userId: {}",userId);
        return userEntity;
    }

}
