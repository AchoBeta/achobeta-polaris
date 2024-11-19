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
     * @author yangzhiyao
     * @date 2024/11/19
     * @param memberId 成员ID
     * @return 查询到的成员信息
     */
    UserEntity queryMemberInfo(String memberId);

}
