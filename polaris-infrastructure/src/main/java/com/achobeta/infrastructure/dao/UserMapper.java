package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.UserPO;
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
    UserPO getUserByUserId(String userId);

    /**
     * 更新用户信息
     * @param userPO 用户实体
     */
    void updateUserInfo(UserPO userPO);

    /**
     * 根据手机号查询用户
     * @param phone
     * @return 用户PO
     */
    UserPO getUserByPhone(String phone);
}