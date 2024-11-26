package com.achobeta.domain.team.adapter.repository;

import com.achobeta.domain.user.model.entity.UserEntity;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 成员仓储接口
 * @date 2024/11/17
 */
public interface IMemberRepository {

    /**
     * 删除团队成员
     * @param userId 发起操作的用户userId
     * @param memberId 成员的userId
     * @param teamId 团队的id
     * @date 2024/11/21
     */
    void deleteMember(String userId, String memberId, String teamId);

    /**
     * 添加成员
     * @param userEntity
     * @param userId
     * @param teamId
     * @param positionIds
     */
    void addMember(UserEntity userEntity, String userId, String teamId, List<String> positionIds);

    /**
     * 根据手机号查询对应用户
     * @param phone 添加的团队成员的手机号
     * @return
     */
    UserEntity queryMemberByPhone(String phone);

    /**
     * 修改团队成员信息
     * @param teamId
     * @return
     */
    UserEntity modifyMemberInfo(UserEntity userEntity, String teamId, List<String> addPositions,List<String> deletePositions);

    /**
     * 查询团队成员信息详情
     * @author yangzhiyao
     * @date 2024/11/19
     * @param memberId 成员ID
     * @return 查询到的成员信息
     */
    UserEntity queryMemberInfo(String memberId);
  
    /**
     * 查询团队成员列表
     * @param teamId
     * @return
     */
    List<UserEntity> queryMemberList(String teamId,String lastUserId, Integer limit);

}
