package com.achobeta.domain.auth.service;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 认证验证服务接口
 * @date 2024/11/22
 */
public interface IAuthVerifyService {

    /**
     * 验证用户在某团队的权限
     * @param userId 用户id
     * @param teamId 团队id
     * @return true表示有权限，false表示无权限
     */
    Boolean verify(String userId, String teamId, List<String> neededPermissions);

}
