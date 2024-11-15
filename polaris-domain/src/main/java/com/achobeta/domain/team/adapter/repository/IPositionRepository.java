package com.achobeta.domain.team.adapter.repository;

import com.achobeta.domain.user.model.entity.UserEntity;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 职位仓储接口
 * @date 2024/11/7
 */
public interface IPositionRepository {

    List<UserEntity> queryMemberList(String teamId);

}
