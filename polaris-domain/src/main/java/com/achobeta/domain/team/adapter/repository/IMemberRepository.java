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
     * 查询团队成员列表
     * @param teamId
     * @return
     */
    List<UserEntity> queryMemberList(String teamId,String lastUserId, Integer limit);

}
