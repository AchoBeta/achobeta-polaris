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

    UserPO getMemberByPhone(String phone);

    void addMember(String userId, String teamId);

    void addUser(UserPO userPO);

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

    /**
     * 根据手机号查询用户
     * @param phone
     * @return 用户PO
     */
    UserPO getUserByPhone(String phone);

    /**
     * 删除用户和团队所有关联
     * @param userId
     */
    void deleteMemberTeam(String userId, String operatorId);

    /**
     * 批量添加用户和团队关联
     * @param userId
     * @param teamIds
     */
    void addMemberTeam(String userId, List<String> teamIds, String operatorId);
}