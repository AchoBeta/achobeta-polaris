package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.user.adapter.repository.IUserRepository;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.dao.po.UserPO;
import com.achobeta.infrastructure.redis.IRedisService;
import com.achobeta.types.common.RedisKey;
import com.achobeta.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static com.achobeta.types.enums.GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST;

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
    public void updateUserInfo(UserEntity userEntity) {
        log.info("查询用户是否存在，userId: {}",userEntity.getUserId());
        // 这里保证用户存在，不能查缓存的得直接查数据库的
        UserPO userPO = userMapper.getUserByUserId(userEntity.getUserId());
        if (userPO == null) {
            log.error("用户不存在！userId：{}",userEntity.getUserId());
            throw new AppException(String.valueOf(USER_ACCOUNT_NOT_EXIST.getCode()),
                    USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        log.info("更新用户信息：{}",userEntity.getUserId());
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
        log.info("更新用户信息成功，userId: {}",userEntity.getUserId());

        log.info("更新用户信息缓存，userId: {}",userEntity.getUserId());
        redisService.remove(RedisKey.USER_INFO + userEntity.getUserId());
        // 注意这里修改了的是userEntity的，无法改的取userPO的
        redisService.setValue(
                RedisKey.USER_INFO + userEntity.getUserId(),
                UserEntity.builder()
                        .userId(userPO.getUserId())
                        .userName(userEntity.getUserName())
                        .phone(userPO.getPhone())
                        .gender(userEntity.getGender())
                        .idCard(userEntity.getIdCard())
                        .email(userEntity.getEmail())
                        .grade(userEntity.getGrade())
                        .major(userEntity.getMajor())
                        .studentId(userEntity.getStudentId())
                        .experience(userEntity.getExperience())
                        .currentStatus(userEntity.getCurrentStatus())
                        .entryTime(userPO.getEntryTime())
                        .likeCount(userPO.getLikeCount())
                        .build());
        log.info("更新用户信息缓存成功，userId: {}",userEntity.getUserId());
    }

}
