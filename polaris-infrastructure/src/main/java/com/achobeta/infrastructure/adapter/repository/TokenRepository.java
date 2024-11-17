package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.login.adapter.repository.ITokenRepository;
import com.achobeta.domain.login.model.valobj.TokenVO;
import com.achobeta.infrastructure.redis.RedissonService;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.enums.RedisKey;
import com.achobeta.types.exception.AppException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * @Author: 严豪哲
 * @Description: token仓储层接口
 * @Date: 2024/11/12 19:15
 * @Version: 1.0
 */

@Repository
public class TokenRepository implements ITokenRepository {

    @Resource
    private RedissonService redissonService;

    //一分钟
    private long MIN = 60*1000;

    //一小时
    private long HOUR = 60*60*1000;

    //一天
    private long DAY = 24*60*60*1000;

    @Override
    public void storeAccessToken(String token, String userId, String phone, String devicId, String ip) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;
        redissonService.addToMap(key, "user_id",userId);
        redissonService.addToMap(key, "phone",phone);
        redissonService.addToMap(key, "device_id",devicId);
        redissonService.addToMap(key, "ip",ip);
        redissonService.addToMap(key, "type","AT");
        redissonService.addToMap(key, "is_deleted","0");

        // 存储设备id和token的关联关系
        redissonService.addToSet(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + devicId, token);

        //设置该token的生存时间
        redissonService.setMapExpired(key,5*MIN);
    }

    @Override
    public void storeReflashToken(String token, String userId, String phone, String devicId, String ip, Boolean isAutoLogin) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;
        redissonService.addToMap(key, "user_id",userId);
        redissonService.addToMap(key, "phone",phone);
        redissonService.addToMap(key, "device_id",devicId);
        redissonService.addToMap(key, "ip",ip);
        if(isAutoLogin){
            redissonService.addToMap(key, "type","RT30");

            //设置该token的生存时间
            redissonService.setMapExpired(key,12*HOUR);
        }else{
            redissonService.addToMap(key, "type","RT12");

            //设置该token的生存时间
            redissonService.setMapExpired(key,30*DAY);
        }
        redissonService.addToMap(key, "is_deleted","0");

        // 存储设备id和token的关联关系
        redissonService.addToSet(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + devicId, token);
    }

    @Override
    public void deleteAccessToken(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;

        // 取出token对应的设备id
        String deviceId = redissonService.getFromMap(key, "device_id");

        // 删除token
        redissonService.addToMap(key, "is_deleted","1");

        // 删除设备id和token的关联关系
        redissonService.removeFromSet(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId, token);
    }

    @Override
    public void deleteReflashToken(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;

        // 取出token对应的设备id
        String deviceId = redissonService.getFromMap(key, "device_id");

        // 删除token
        redissonService.addToMap(key, "is_deleted","1");

        // 删除设备id和token的关联关系
        redissonService.removeFromSet(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId, token);
    }

    @Override
    public void deleteTokenByDeviceId(String deviceId) {

        // 取出设备id对应的token列表
        Set<String> tokens = redissonService.getSetMembers(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId);

        // 逐个删除token
        for (String token : tokens) {
            String key = RedisKey.TOKEN.getKeyPrefix() + token;
            String isDeleted = redissonService.getFromMap(key, "is_deleted");
            if(null==isDeleted||isDeleted.equals("1")){
                continue;
            }
            redissonService.addToMap(key, "is_deleted","1");
        }

        // 删除设备id和所有token的关联关系
        redissonService.remove(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId);
    }

    @Override
    public Boolean checkToken(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;
        String isDeleted = redissonService.getFromMap(key, "is_deleted");
        if(null==isDeleted||isDeleted.equals("1")){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void resetReflashTokenExpired(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;

        String type = redissonService.getFromMap(key, "type");

        if(type != null){
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_EXPIRED.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_EXPIRED.getMessage());
        }
        else if(type.equals("AT")){
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getMessage());
        }
        else if(type.equals("RT12")){
            redissonService.setMapExpired(key,12*HOUR);
        }
        else if(type.equals("RT30")){
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getMessage());
        }
        else {
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getMessage());
        }

    }

    @Override
    public TokenVO getReflashTokenInfo(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;

        Map<String, String> javaMap = redissonService.getMapToJavaMap(key);

        if(null == javaMap || javaMap.isEmpty()){
            return null;
        }

        Boolean isAutoLogin;
        if(javaMap.get("type").equals("RT7")) {
            isAutoLogin = false;
        }else{
            isAutoLogin = true;
        }

        return TokenVO.builder()
               .userId(Long.valueOf(javaMap.get("user_id")))
               .phone(javaMap.get("phone"))
               .deviceId(javaMap.get("device_id"))
               .ip(javaMap.get("ip"))
               .isAutoLogin(isAutoLogin)
               .build();
    }
}
