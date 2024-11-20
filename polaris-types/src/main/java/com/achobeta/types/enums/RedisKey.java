package com.achobeta.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author: 严豪哲
 * @Description: redis的key前缀
 * @Date: 2024/11/10 23:26
 * @Version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum RedisKey {
    CODE("ab:polaris:login:code:","验证码"),
    RATE_LIMIT("ab:polaris:login:rateLimit:","发送验证码频率限制"),
    TOKEN("ab:polaris:login:token:","token"),
    DEVICE_TO_TOKEN("ab:polaris:login:device_to_token:","设备id关联token"),
    ;

    private String keyPrefix;
    private String info;
}
