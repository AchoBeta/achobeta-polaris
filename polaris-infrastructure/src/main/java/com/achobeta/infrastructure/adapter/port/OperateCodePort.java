package com.achobeta.infrastructure.adapter.port;

import com.achobeta.domain.login.adapter.port.IOperateCodePost;
import com.achobeta.infrastructure.redis.RedissonService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 严豪哲
 * @Description: 操作redis中的验证码外部接口实现
 * @Date: 2024/11/8 20:09
 * @Version: 1.0
 */

@Component
public class OperateCodePort implements IOperateCodePost {

    @Resource
    private RedissonService redissonService;

    private final int LIMIT = 10; // 每分钟最多调用次数
    private final int EXPIRE_TIME = 60*1000; // 过期时间(毫秒)
    private final String KEY = "rateLimit";

    @Override
    public String getCodeByPhone(String phone) {
        String value = redissonService.<String>getValue(phone);
        return value;
    }

    @Override
    public void setCode(String phone, String code, long expired) {
        redissonService.setValue(phone,code,expired);
    }

    @Override
    public boolean checkRateLimit() {
        Integer value = redissonService.<Integer>getValue(KEY);
        if(value == null){
            redissonService.setValue(KEY,1,EXPIRE_TIME);
            return true;
        } else if(value<LIMIT){
            redissonService.incr(KEY);
            return true;
        }else{
            return false;
        }
    }
}
