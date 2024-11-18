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
     * 查询用户所属团队
     * 判断是否已在团队中
     * @param phone 添加的团队成员的手机号
     * @return
     */
    List<String> queryTeamsOfMember(String phone);

}
