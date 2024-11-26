package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author yangzhiyao
 * @description UserDao接口
 * @date 2024/11/5
 */
@Mapper
public interface UserMapper {

    List<UserPO> listMemberByTeamId(String teamId,Long lastId, Integer limit);

    Long getIdByUserId(String userId);

    /**
     * 根据userId获取用户信息
     * @param userId 用户业务id
     * @return 用户实体
     */
    UserPO getUserByUserId(String userId);

    /**
     * 更新修改团队成员信息
     * @param userPO
     */
    void updateMemberInfo(UserPO userPO);

    /**
     * 更新用户信息
     * @param userPO 用户实体
     */
    void updateUserInfo(UserPO userPO);

}