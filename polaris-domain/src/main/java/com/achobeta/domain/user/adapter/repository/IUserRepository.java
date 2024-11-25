package com.achobeta.domain.user.adapter.repository;

import com.achobeta.domain.user.model.entity.UserEntity;

/**
 * @author yangzhiyao
 * @description 用户仓储接口
 * @date 2024/11/6
 */
public interface IUserRepository {

    /**
     * 修改个人信息
     */
    void updateUserInfo(UserEntity userEntity);
    
    /**
     * 查询个人信息
     */
    UserEntity queryUserInfo(String userId);


}
