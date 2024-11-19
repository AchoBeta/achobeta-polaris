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

    /*
     * 用手机号在redis中获取验证码
     * @param phone 手机号
     * @return 验证码
     */
    @Override
    public String getCodeByPhone(String phone) {
        return redissonService.<String>getValue(RedisKey.CODE.getKeyPrefix()+phone);
    }

    /*
     * 在redis中存储验证码
     * @param phone 手机号
     * @param code 验证码
     * @param expired 过期时间
     */
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

    @Override
    public void deleteCode(String phone, String code) {
        String key=RedisKey.CODE.getKeyPrefix()+phone;

        try {
            redissonService.remove(key);
        } catch (Exception e) {
            //删除失败则不做处理
        }
    }

    @Override
    public void lockCheckCode(String phone, String code) {
        String key=RedisKey.CODE_LOCK.getKeyPrefix()+"phone "+phone+" code "+code;
        redissonService.getLock(key);
    }

    @Override
    public void unlockCheckCode(String phone, String code) {
        String key=RedisKey.CODE_LOCK.getKeyPrefix()+"phone "+phone+" code "+code;
        redissonService.unLock(key);
    }

}
