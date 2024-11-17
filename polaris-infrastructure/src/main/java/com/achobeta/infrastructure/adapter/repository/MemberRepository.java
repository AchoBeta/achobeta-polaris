package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.team.adapter.repository.IMemberRepository;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.dao.po.UserPO;
import com.achobeta.infrastructure.redis.IRedisService;
import com.achobeta.types.common.Constants;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 团队成员仓储接口
 * @date 2024/11/17
 */
@Slf4j
@Repository
public class MemberRepository implements IMemberRepository {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private IRedisService redisService;

    @Override
    public UserEntity modifyMemberInfo(UserEntity userEntity, String teamId, List<String> addPositions, List<String> deletePositions) {
        // 这里保证用户存在，不能查缓存的得直接查数据库的
        UserPO userPO = userMapper.getUserByUserId(userEntity.getUserId());
        if (userPO == null) {
            log.error("用户不存在！userId：{}",userEntity.getUserId());
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getCode()),
                    GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }

        positionMapper.addPositionToUser(addPositions, userEntity.getUserId());
        positionMapper.deletePositionWithUser(deletePositions, userEntity.getUserId());

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

        redisService.remove(Constants.USER_INFO + userEntity.getUserId());
        return userEntity;
    }

}
