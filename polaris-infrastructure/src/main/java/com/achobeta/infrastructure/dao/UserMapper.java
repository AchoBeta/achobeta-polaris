package com.achobeta.infrastructure.dao;

import com.achobeta.domain.user.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yangzhiyao
 * @description UserDao接口
 * @date 2024/11/5
 */
@Mapper
public interface UserMapper {
    /**
     * 根据userId获取用户信息
     * @param userId 用户业务id
     * @return 用户实体
     */
    UserEntity getUserByUserId(String userId);
}