package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.login.adapter.repository.ITokenRepository;
import com.achobeta.domain.login.model.valobj.TokenVO;
import com.achobeta.infrastructure.redis.RedissonService;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.common.RedisKey;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.util.TokenUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    private final String USER_ID = "user_id";
    private final String PHONE = "phone";
    private final String DEVICE_ID = "device_id";
    private final String IP = "ip";
    private final String TYPE = "type";
    private final String IS_DELETED = "is_deleted";
    private final String ACCESS_TOKEN = "access_token";
    private final String REFRESH_TOKEN = "refresh_token";
    private final String UNDELETED = "0";
    private final String DELETED = "1";
    private final String FINGERPRINTING = "fingerPrinting";
    private final long MINUTE1 = TimeUnit.MINUTES.toMillis(1);
    private final long MINUTE5 = TimeUnit.MINUTES.toMillis(5);
    private final long HOUR12 = TimeUnit.HOURS.toMillis(12);
    private final long DAY30 = TimeUnit.DAYS.toMillis(30);


    @Override
    public void storeAccessToken(String token, String userId, String phone, String deviceId, String ip, String fingerPrinting) {
        String key = RedisKey.TOKEN + token;
        redissonService.addToMap(key, USER_ID, userId);
        redissonService.addToMap(key, PHONE, phone);
        redissonService.addToMap(key, DEVICE_ID, deviceId);
        redissonService.addToMap(key, IP, ip);
        redissonService.addToMap(key, TYPE, TokenUtil.ACCESS_TOKEN);
        redissonService.addToMap(key, IS_DELETED, UNDELETED);
        redissonService.addToMap(key, FINGERPRINTING, fingerPrinting);

        //找出前一个AT，如果有就删除
        String preAT = redissonService.getFromMap(RedisKey.DEVICE_TO_TOKEN + deviceId, ACCESS_TOKEN);
        if (preAT != null) {
            redissonService.addToMap(RedisKey.TOKEN + preAT, IS_DELETED, DELETED);
        }

        // 存储设备id和token的关联关系
        redissonService.addToMap(RedisKey.DEVICE_TO_TOKEN + deviceId, ACCESS_TOKEN, token);

        //设置该token的生存时间
        redissonService.setMapExpired(key,MINUTE5);
    }

    @Override
    public void storeReflashToken(String token, String userId, String phone, String deviceId, String ip, Boolean isAutoLogin, String fingerPrinting) {
        String key = RedisKey.TOKEN + token;
        redissonService.addToMap(key, USER_ID, userId);
        redissonService.addToMap(key, PHONE, phone);
        redissonService.addToMap(key, DEVICE_ID, deviceId);
        redissonService.addToMap(key, IP, ip);
        redissonService.addToMap(key, FINGERPRINTING, fingerPrinting);

        // 存储设备id和token的关联关系
        redissonService.addToMap(RedisKey.DEVICE_TO_TOKEN + deviceId, REFRESH_TOKEN, token);

        if (!isAutoLogin) {
            redissonService.addToMap(key, TYPE, TokenUtil.REFRESH_TOKEN_TYPE[0]);

            //设置该token的生存时间
            redissonService.setMapExpired(key,HOUR12);

            //设置该关联关系的生存时间
            redissonService.setMapExpired(RedisKey.DEVICE_TO_TOKEN + deviceId, HOUR12 + MINUTE5 + MINUTE1);
        } else {
            redissonService.addToMap(key, TYPE, TokenUtil.REFRESH_TOKEN_TYPE[1]);

            //设置该token的生存时间
            redissonService.setMapExpired(key,DAY30);

            //设置该关联关系的生存时间
            redissonService.setMapExpired(RedisKey.DEVICE_TO_TOKEN + deviceId, DAY30 + MINUTE5 + MINUTE1);
        }

        redissonService.addToMap(key, IS_DELETED, UNDELETED);
    }

    @Override
    public void deleteAccessToken(String token) {
        String key = RedisKey.TOKEN + token;

        // 取出token对应的设备id
        String deviceId = redissonService.getFromMap(key, DEVICE_ID);

        // 删除token
        redissonService.addToMap(key, IS_DELETED, DELETED);

        // 删除设备id和token的关联关系
        redissonService.removeFromSet(RedisKey.DEVICE_TO_TOKEN + deviceId, token);
    }

    @Override
    public void deleteReflashToken(String token) {
        String key = RedisKey.TOKEN + token;

        // 取出token对应的设备id
        String deviceId = redissonService.getFromMap(key, DEVICE_ID);

        // 删除token
        redissonService.addToMap(key, IS_DELETED, DELETED);

        // 删除设备id和token的关联关系
        redissonService.removeFromMap(RedisKey.DEVICE_TO_TOKEN + deviceId, REFRESH_TOKEN);
    }

    @Override
    public void deleteTokenByDeviceId(String deviceId) {

        // 取出设备id对应的token列表
        Map<String, String> tokens = redissonService.getMapToJavaMap(RedisKey.DEVICE_TO_TOKEN + deviceId);

        // 逐个删除token
        for (Map.Entry<String, String> token : tokens.entrySet()) {

            String key = RedisKey.TOKEN + token.getValue();

            redissonService.remove(key);
        }

        // 删除设备id和所有token的关联关系
        redissonService.remove(RedisKey.DEVICE_TO_TOKEN + deviceId);

    }

    @Override
    public int checkAccessToken(String token) {
        String key = RedisKey.TOKEN + token;
        String isDeleted = redissonService.getFromMap(key, IS_DELETED);
        if (null == isDeleted) {
            //此时token不存在,已过期
            return 0;
        } else if (isDeleted.equals(DELETED)) {
            //此时token已被删除
            return -1;
        } else {
            //此时token有效
            return 1;
        }
    }

    @Override
    public Boolean checkToken(String token) {
        String key = RedisKey.TOKEN + token;
        String isDeleted = redissonService.getFromMap(key, IS_DELETED);
        return isDeleted != null && isDeleted.equals(UNDELETED);
    }

    @Override
    public void resetReflashTokenExpired(String token) {
        String key = RedisKey.TOKEN + token;

        String type = redissonService.getFromMap(key, TYPE);

        String deviceId = redissonService.getFromMap(key, DEVICE_ID);

        if (type == null) {
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_EXPIRED.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_EXPIRED.getMessage());
        } else if (type.equals(TokenUtil.REFRESH_TOKEN_TYPE[0])) {

            //重置token的有效时间
            redissonService.setMapExpired(key, MINUTE5);

            //重置关联关系的有效时间
            redissonService.setMapExpired(RedisKey.DEVICE_TO_TOKEN + deviceId, HOUR12 + MINUTE5 + MINUTE1);
        } else {
            //类型为access_token或者reflash_token30或者其他
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getMessage());
        }

    }

    @Override
    public TokenVO getReflashTokenInfo(String token) {
        String key = RedisKey.TOKEN + token;

        Map<String, String> javaMap = redissonService.getMapToJavaMap(key);

        if (null == javaMap || javaMap.isEmpty()) {
            return null;
        }

        Boolean isAutoLogin = javaMap.get(TYPE).equals(TokenUtil.REFRESH_TOKEN_TYPE[1]);

        return TokenVO.builder()
                .userId(Long.valueOf(javaMap.get(USER_ID)))
                .phone(javaMap.get(PHONE))
                .deviceId(javaMap.get(DEVICE_ID))
                .ip(javaMap.get(IP))
                .isAutoLogin(isAutoLogin)
                .fingerPrinting(javaMap.get(FINGERPRINTING))
                .build();
    }

    @Override
    public TokenVO getAccessTokenInfo(String token) {
        String key = RedisKey.TOKEN + token;

        Map<String, String> javaMap = redissonService.getMapToJavaMap(key);

        if (null == javaMap || javaMap.isEmpty()) {
            return null;
        }

        return TokenVO.builder()
                .userId(Long.valueOf(javaMap.get(USER_ID)))
                .phone(javaMap.get(PHONE))
                .deviceId(javaMap.get(DEVICE_ID))
                .ip(javaMap.get(IP))
                .fingerPrinting(javaMap.get(FINGERPRINTING))
                .build();
    }

    @Override
    public Long getAccessTokenExpired(String token) {
        String key = RedisKey.TOKEN + token;
        return redissonService.getMapExpired(key);

    }
}
