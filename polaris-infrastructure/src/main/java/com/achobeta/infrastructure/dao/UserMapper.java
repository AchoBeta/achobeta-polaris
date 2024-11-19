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

    UserPO getMemberByPhone(String phone);

    void addMember(String userId, String teamId);

    void addUser(UserPO userPO);

}