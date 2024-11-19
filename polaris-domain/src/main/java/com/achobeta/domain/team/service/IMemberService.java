package com.achobeta.domain.team.service;

import com.achobeta.domain.user.model.entity.UserEntity;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 成员服务接口
 * @date 2024/11/17
 */
public interface IMemberService {

    /**
     * 添加成员方法接口
     * @param userEntity
     * @param userId
     * @param teamId
     * @param positionIds
     */
    UserEntity addMember(UserEntity userEntity, String userId, String teamId, List<String> positionIds);

}
