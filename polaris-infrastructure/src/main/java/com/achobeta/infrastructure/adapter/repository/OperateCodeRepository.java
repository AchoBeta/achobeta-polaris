package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.login.adapter.repository.IOperateCodeRepository;
import com.achobeta.infrastructure.redis.RedissonService;
import com.achobeta.types.enums.RedisKey;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author: 严豪哲
 * @Description: 操作redis中的验证码仓储层实现
 * @Date: 2024/11/8 20:09
 * @Version: 1.0
 */

@Repository
public class OperateCodeRepository implements IOperateCodeRepository {

    private final long EXPIRED = 55*1000;

    @Resource
    private RedissonService redissonService;

    @Override
    public String getCodeByPhone(String phone) {
        return redissonService.<String>getValue(RedisKey.CODE.getKeyPrefix()+phone);
    }

    @Override
    public void setCode(String phone, String code, long expired) {
        String key=RedisKey.CODE.getKeyPrefix()+phone;
        redissonService.setValue(key,code,expired);
    }

    @Override
    public void setRateLimit(String phone) {
        String key=RedisKey.RATE_LIMIT.getKeyPrefix()+phone;
        redissonService.setValue(key,1,EXPIRED);
    }

    @Override
    public Boolean checkRateLimit(String phone) {
        String key=RedisKey.RATE_LIMIT.getKeyPrefix()+phone;

        if(null == redissonService.getValue(key)){
            return true;
        }
        else{
            return false;
        }
    }
}
