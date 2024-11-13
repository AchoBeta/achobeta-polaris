package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.user.adapter.repository.IUserRepository;
import com.achobeta.infrastructure.dao.UserMapper;
import com.achobeta.infrastructure.redis.IRedisService;
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

}
