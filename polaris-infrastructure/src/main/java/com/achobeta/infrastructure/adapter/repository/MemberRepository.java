package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.team.adapter.repository.IMemberRepository;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.infrastructure.dao.PositionMapper;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.dao.po.UserPO;
import com.achobeta.infrastructure.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addMember(UserEntity userEntity,String userId, String teamId, List<String> positionIds) {
        userMapper.addUser(UserPO.builder()
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
                .build());
        userMapper.addMember(userEntity.getUserId(), teamId);
        positionMapper.addPositionsToMember(userId, userEntity.getUserId(), positionIds);
    }

    @Override
    public List<String> queryTeamsOfMember(String phone) {
        return userMapper.listTeamIdByUserId(phone);
    }
}
