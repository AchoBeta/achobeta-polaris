package com.achobeta.domain.login.adapter.repository;

import com.achobeta.domain.user.model.entity.UserEntity;

/**
 * @Author: 严豪哲
 * @Description: 新建设备 获取设备 查询用户 存储token
 * @Date: 2024/11/10 16:19
 * @Version: 1.0
 */

public interface IUserRepository {
    UserEntity getUserByPhone(String phone);
}
