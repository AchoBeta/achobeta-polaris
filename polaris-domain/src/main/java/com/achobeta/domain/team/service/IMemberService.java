package com.achobeta.domain.team.service;

/**
 * @author yangzhiyao
 * @description 成员服务接口
 * @date 2024/11/17
 */
public interface IMemberService {

    /**
     * 删除成员
     * @param userId 发出操作的用户id
     * @param memberId 被删除的成员id
     * @param teamId 团队id
     * @date 2024/11/21
     */
   void deleteMember(String userId, String memberId, String teamId);

}
