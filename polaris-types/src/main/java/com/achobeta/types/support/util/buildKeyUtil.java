package com.achobeta.types.support.util;

import com.achobeta.types.common.RedisKey;

/**
 * @author yangzhiyao
 * @description 构建key
 */
public class buildKeyUtil {

    public static String buildUserInfoKey(String userId) {
        return RedisKey.USER_INFO + userId;
    }
}
