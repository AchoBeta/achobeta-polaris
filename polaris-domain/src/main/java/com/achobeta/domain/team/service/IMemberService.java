package com.achobeta.domain.team.service;

import com.achobeta.domain.user.model.entity.UserEntity;

import java.util.List;

/**
 * @author yangzhiyao
 * @description 成员服务接口
 * @date 2024/11/17
 */
public interface IMemberService {

    UserEntity modifyMember(String teamId, UserEntity userEntity, List<String> addPositions, List<String> deletePositions);

}
