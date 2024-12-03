package com.achobeta.infrastructure.dao;

import com.achobeta.infrastructure.dao.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    void deleteMember(@Param("userId") String userId,@Param("memberId") String memberId,@Param("teamId") String teamId);

    /**
     * 逻辑删除用户职位关系
     * @param userId
     */
    void deleteUserPosition(@Param("userId")String userId,@Param("memberId") String memberId,@Param("teamId") String teamId);

    UserPO getMemberByPhone(@Param("phone") String phone);

    void addMember(@Param("userId") String userId,@Param("teamId")String teamId);

    void addUser(UserPO userPO);

    List<UserPO> listMemberByTeamId(@Param("teamId") String teamId,@Param("lastId") Long lastId,@Param("limit") Integer limit);

    Long getIdByUserId(@Param("userId") String userId);

    /**
     * 根据userId获取用户信息
     * @param userId 用户业务id
     * @return 用户实体
     */
    UserPO getUserByUserId(@Param("userId") String userId);

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
    UserPO getUserByPhone(@Param("phone") String phone);

    /**
     * 删除用户和团队所有关联
     * @param userId
     */
    void deleteMemberTeam(@Param("userId")String userId,@Param("operatorId") String operatorId);

    /**
     * 批量添加用户和团队关联
     * @param userId
     * @param teamIds
     */
    void addMemberTeam(@Param("userId")String userId,@Param("teamIds") List<String> teamIds,@Param("operatorId") String operatorId);
}