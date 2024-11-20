package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.team.adapter.repository.IMemberRepository;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.redis.RedissonService;
import com.achobeta.types.common.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

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
    private RedissonService redisService;

    @Override
    public void deleteMember(String userId, String memberId, String teamId) {

        log.info("成员表中删除成员，userId:{}, memberId:{}", userId, memberId);
        userMapper.deleteMember(userId, memberId, teamId);

        log.info("用户职位关联表删除关联数据，userId:{}, memberId:{}，teamId:{}", userId, memberId, teamId);
        userMapper.deleteUserPosition(userId, memberId, teamId);
    }

    @Override
    public void deleteUser(String userId, String deleteUserId) {
        log.info("清除数据库用户信息缓存，userId:{}", userId);
        redisService.remove(RedisKey.USER_INFO + deleteUserId);

        log.info("用户表中删除用户，userId:{}, deleteUserId:{}", userId, deleteUserId);
        userMapper.deleteUser(userId, deleteUserId);
    }

}
