package com.achobeta.types.common;

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
public class RedisKey {

    public static final String CODE = "ab:polaris:login:code:";
    public static final String RATE_LIMIT = "ab:polaris:login:rateLimit:";
    public static final String TOKEN = "ab:polaris:login:token:";
    public static final String DEVICE_TO_TOKEN = "ab:polaris:login:device_to_token:";
    public static final String CODE_LOCK = "ab:polaris:login:code_lock:";

}
