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
     * 添加成员
     * @param userEntity
     * @param userId
     * @param teamId
     * @param positionIds
     */
    void addMember(UserEntity userEntity, String userId, String teamId, List<String> positionIds);

    /**
     * 根据手机号查询所有团队中存在的对应用户
     * @param phone 添加的团队成员的手机号
     * @return
     */
    UserEntity queryMemberByPhone(String phone);

}
