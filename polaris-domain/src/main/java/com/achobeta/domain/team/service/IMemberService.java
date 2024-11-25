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
     * 修改团队成员信息
     * @param teamId 团队id
     * @param userEntity 用户实体
     * @param addPositions 新增职位
     * @param deletePositions 删除职位
     * @return 修改后的用户实体
     */
    UserEntity modifyMember(String teamId, UserEntity userEntity, List<String> addPositions, List<String> deletePositions);

    /**
     * 查询成员信息详情
     * @author yangzhiyao
     * @date 2024/11/19
     * @param memberId 成员ID
     * @return 查询到的成员信息
     */
    UserEntity queryMemberInfo(String memberId);

    /**
     * 查看团队成员列表
     * @param teamId 团队ID
     * @param lastId 上次查询的最后一条记录ID
     * @param limit 单次查询一页的记录数
     * @return 成员列表
     */
    List<UserEntity> queryMembers(String teamId,String lastId, Integer limit);

}
