package com.achobeta.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author yangzhiyao
 * @description UserDao接口
 * @date 2024/11/5
 */
@Mapper
public interface UserMapper {

    /**
     * 逻辑删除用户
     * @param userId
     */
    void deleteUser(String userId, String deleteUserId);

    /**
     * 逻辑删除团队成员
     * @param userId
     */
    void deleteMember(String userId, String memberId, String teamId);

    /**
     * 逻辑删除用户职位关系
     * @param userId
     */
    void deleteUserPosition(String userId, String memberId, String teamId);

    /**
     * 获取成员的团队数量
     * @param memberId
     * @return
     */
    Integer countTeamOfMember(String memberId);

}