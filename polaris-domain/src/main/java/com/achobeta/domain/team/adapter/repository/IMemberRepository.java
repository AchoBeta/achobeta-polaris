package com.achobeta.domain.team.adapter.repository;

import com.achobeta.domain.user.model.entity.UserEntity;

/**
 * @author yangzhiyao
 * @description 成员仓储接口
 * @date 2024/11/17
 */
public interface IMemberRepository {

    /**
     * 查询团队成员信息详情
     * @param memberId
     * @return
     */
    UserEntity queryMemberInfo(String memberId);

}
