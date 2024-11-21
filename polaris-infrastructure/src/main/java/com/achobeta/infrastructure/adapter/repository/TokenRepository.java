package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.login.adapter.repository.ITokenRepository;
import com.achobeta.domain.login.model.valobj.TokenVO;
import com.achobeta.infrastructure.redis.RedissonService;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.enums.RedisKey;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.util.TokenUtil;
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

    private String USER_ID = "user_id";
    private String PHONE = "phone";
    private String DEVICE_ID = "device_id";
    private String IP = "ip";
    private String TYPE = "type";
    private String IS_DELETED = "is_deleted";
    private String ACCESS_TOKEN = "access_token";
    private String REFRESH_TOKEN = "refresh_token";

    @Override
    public void storeAccessToken(String token, String userId, String phone, String deviceId, String ip) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;
        redissonService.addToMap(key, USER_ID,userId);
        redissonService.addToMap(key, PHONE,phone);
        redissonService.addToMap(key, DEVICE_ID,deviceId);
        redissonService.addToMap(key, IP,ip);
        redissonService.addToMap(key, TYPE, TokenUtil.ACCESS_TOKEN);
        redissonService.addToMap(key, IS_DELETED,"0");

        // 存储设备id和token的关联关系
        redissonService.addToMap(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId, ACCESS_TOKEN, token);

        //设置该token的生存时间
        redissonService.setMapExpired(key,5*MIN);
    }

    @Override
    public void storeReflashToken(String token, String userId, String phone, String deviceId, String ip, Boolean isAutoLogin) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;
        redissonService.addToMap(key, USER_ID,userId);
        redissonService.addToMap(key, PHONE,phone);
        redissonService.addToMap(key, DEVICE_ID,deviceId);
        redissonService.addToMap(key, IP,ip);
        redissonService.addToMap(key, IS_DELETED,"0");

        // 存储设备id和token的关联关系
        redissonService.addToMap(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId, REFRESH_TOKEN, token);

        if(!isAutoLogin){
            redissonService.addToMap(key, TYPE,TokenUtil.REFRESH_TOKEN_TYPE[0]);

            //设置该token的生存时间
            redissonService.setMapExpired(key,12*HOUR);

            //设置该关联关系的生存时间
            redissonService.setMapExpired(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId, 12*HOUR + 6*MIN);
        }else{
            redissonService.addToMap(key, TYPE,TokenUtil.REFRESH_TOKEN_TYPE[1]);

            //设置该token的生存时间
            redissonService.setMapExpired(key,30*DAY);

            //设置该关联关系的生存时间
            redissonService.setMapExpired(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId, 30*DAY + 6*MIN);
        }

    }

    @Override
    public void deleteAccessToken(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;

        // 取出token对应的设备id
        String deviceId = redissonService.getFromMap(key, DEVICE_ID);

        // 删除token
        redissonService.addToMap(key, IS_DELETED,"1");

        // 删除设备id和token的关联关系
        redissonService.removeFromMap(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId, ACCESS_TOKEN);
    }

    @Override
    public void deleteReflashToken(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;

        // 取出token对应的设备id
        String deviceId = redissonService.getFromMap(key, DEVICE_ID);

        // 删除token
        redissonService.addToMap(key, IS_DELETED,"1");

        // 删除设备id和token的关联关系
        redissonService.removeFromMap(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId, REFRESH_TOKEN);
    }

    @Override
    public void deleteTokenByDeviceId(String deviceId) {

        // 取出设备id对应的token列表
        Map<String,String> tokens = redissonService.getMapToJavaMap(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId);

        // 逐个删除token
        for (Map.Entry<String, String> token : tokens.entrySet()) {

            String key = RedisKey.TOKEN.getKeyPrefix() + token.getValue();

            redissonService.remove(key);
        }

        // 删除设备id和所有token的关联关系
        redissonService.remove(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId);

    }

    @Override
    public Boolean checkToken(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;
        String isDeleted = redissonService.getFromMap(key, IS_DELETED);
        if(null==isDeleted||isDeleted.equals("1")){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public int checkAccessToken(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;
        String isDeleted = redissonService.getFromMap(key, IS_DELETED);
        if(null==isDeleted){
            //此时token不存在,已过期
            return 0;
        }
        else if(isDeleted.equals("1")){
            //此时token已被删除
            return -1;
        }
        else{
            //此时token有效
            return 1;
        }
    }

    @Override
    public void resetReflashTokenExpired(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;

        String type = redissonService.getFromMap(key, TYPE);

        String deviceId = redissonService.getFromMap(key, DEVICE_ID);

        if(type == null){
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_EXPIRED.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_EXPIRED.getMessage());
        }
        else if(type.equals(TokenUtil.REFRESH_TOKEN_TYPE[0])){

            //重置token的有效时间
            redissonService.setMapExpired(key,12*HOUR);

            //重置关联关系的有效时间
            redissonService.setMapExpired(RedisKey.DEVICE_TO_TOKEN.getKeyPrefix() + deviceId, 12*HOUR + 6*MIN);
        }
        else {
            //类型为access_token或者reflash_token30或者其他
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
        if(javaMap.get(TYPE).equals(TokenUtil.REFRESH_TOKEN_TYPE[0])) {
            isAutoLogin = false;
        }else{
            isAutoLogin = true;
        }

        return TokenVO.builder()
               .userId(Long.valueOf(javaMap.get(USER_ID)))
               .phone(javaMap.get(PHONE))
               .deviceId(javaMap.get(DEVICE_ID))
               .ip(javaMap.get(IP))
               .isAutoLogin(isAutoLogin)
               .build();
    }

    @Override
    public TokenVO getAccessTokenInfo(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;

        Map<String, String> javaMap = redissonService.getMapToJavaMap(key);

        if(null == javaMap || javaMap.isEmpty()){
            return null;
        }

        return TokenVO.builder()
                .userId(Long.valueOf(javaMap.get(USER_ID)))
                .phone(javaMap.get(PHONE))
                .deviceId(javaMap.get(DEVICE_ID))
                .ip(javaMap.get(IP))
                .build();
    }

    @Override
    public Long getAccessTokenExpired(String token) {
        String key = RedisKey.TOKEN.getKeyPrefix() + token;
        return redissonService.getMapExpired(key);

    }

}
