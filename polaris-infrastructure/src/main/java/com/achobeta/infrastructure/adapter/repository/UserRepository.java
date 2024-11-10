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
    public UserEntity queryUserInfo(String userId) {
        // 从redis中获取用户信息
        UserEntity userEntity = redisService.getValue(Constants.RedisKeyPrefix.USER_INFO + userId);
        if(userEntity!= null) {
            return userEntity;
        }

        // 从数据库中获取用户信息
        UserPO userPO = userMapper.getUserByUserId(userId);
        if(userPO == null) {
            log.error("用户不存在！userId：{}",userId);
            throw new AppException(Constants.ResponseCode.USER_NOT_EXIST.getCode(),
                    Constants.ResponseCode.USER_NOT_EXIST.getInfo());
        }
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

        // 缓存用户信息到redis
        redisService.setValue(Constants.RedisKeyPrefix.USER_INFO + userId,userEntity);
        return userEntity;
    }

    @Override
    public void updateUserInfo(UserEntity userEntity) {
        if (userMapper.getUserByUserId(userEntity.getUserId()) == null) {
            log.error("用户不存在！userId：{}",userEntity.getUserId());
            throw new AppException(Constants.ResponseCode.USER_NOT_EXIST.getCode(),
                    Constants.ResponseCode.USER_NOT_EXIST.getInfo());
        }
        // 更新用户信息到数据库
         userMapper.updateUserInfo(UserPO.builder()
                 .userId(userEntity.getUserId())
                 .userName(userEntity.getUserName())
                 .phone(userEntity.getPhone())
                 .gender(userEntity.getGender())
                 .idCard(userEntity.getIdCard())
                 .email(userEntity.getEmail())
                 .grade(userEntity.getGrade())
                 .major(userEntity.getMajor())
                 .studentId(userEntity.getStudentId())
                 .experience(userEntity.getExperience())
                 .currentStatus(userEntity.getCurrentStatus())
                 .entryTime(userEntity.getEntryTime())
                 .likeCount(userEntity.getLikeCount())
                 .updateBy(userEntity.getUserId())
                 .build());
        // 更新用户信息到redis
        redisService.setValue(Constants.RedisKeyPrefix.USER_INFO + userEntity.getUserId(),userEntity);
    }

}
