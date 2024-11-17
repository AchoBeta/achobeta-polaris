package com.achobeta.domain.team.adapter.repository;

import com.achobeta.domain.user.model.entity.UserEntity;

/**
 * @author yangzhiyao
 * @description 职位仓储接口
 * @date 2024/11/7
 */
public interface IPositionRepository {

    /**
     * 修改成员信息
     * @param userEntity
     * @return
     */
    UserEntity modifyMemberInfo(UserEntity userEntity);

}
