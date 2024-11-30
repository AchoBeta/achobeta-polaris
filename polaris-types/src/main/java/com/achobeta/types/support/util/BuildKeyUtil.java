package com.achobeta.types.support.util;

import com.achobeta.types.common.RedisKey;

/**
 * @author yangzhiyao
 * @description 构建key
 */
public class BuildKeyUtil {

    public static String buildUserInfoKey(String userId) {
        return RedisKey.USER_INFO + userId;
    }

    public static String buildUserRoleInTeamKey(String userId, String teamId) {
        return RedisKey.USER_ROLE_IN_TEAM + userId + teamId;
    }

    public static String buildUserPermissionInTeamKey(String userId, String teamId) {
        return RedisKey.USER_PERMISSION_IN_TEAM + userId + teamId;
    }
}
