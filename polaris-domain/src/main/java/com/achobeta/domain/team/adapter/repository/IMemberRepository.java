package com.achobeta.domain.team.adapter.repository;

/**
 * @author yangzhiyao
 * @description 成员仓储接口
 * @date 2024/11/17
 */
public interface IMemberRepository {

    /**
     * 删除团队成员
     * @param userId 发起操作的用户userId
     * @param memberId 成员的userId
     * @param teamId 团队的id
     * @date 2024/11/21
     */
    void deleteMember(String userId, String memberId, String teamId);

    /**
     * 删除用户
     * @param userId
     * @param deleteUserId
     * @date 2024/11/21
     */
    void deleteUser(String userId, String deleteUserId);

    /**
     * 获取成员的团队数量
     * @param memberId
     * @return
     */
    Integer countTeamOfMember(String memberId);

}
