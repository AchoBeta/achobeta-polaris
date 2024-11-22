package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.login.adapter.repository.IOperateCodeRepository;
import com.achobeta.infrastructure.redis.RedissonService;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.enums.RedisKey;
import com.achobeta.types.exception.AppException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 严豪哲
 * @Description: 操作redis中的验证码仓储层实现
 * @Date: 2024/11/8 20:09
 * @Version: 1.0
 */

@Repository
public class OperateCodeRepository implements IOperateCodeRepository {

    private final long EXPIRED = 55*1000;

    private final String PHONE = "phone";
    private final String CODE = "code";

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

        return redissonService.getValue(key) == null;
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
        String key=RedisKey.CODE_LOCK.getKeyPrefix()+PHONE+phone+CODE+code;
        try {
            redissonService.getLock(key).tryLock(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_UNKNOWN_ERROR.getCode()),GlobalServiceStatusCode.LOGIN_UNKNOWN_ERROR.getMessage());
        }
    }

    @Override
    public void unlockCheckCode(String phone, String code) {
        String key=RedisKey.CODE_LOCK.getKeyPrefix()+PHONE+phone+CODE+code;
        redissonService.unLock(key);
    }

}
