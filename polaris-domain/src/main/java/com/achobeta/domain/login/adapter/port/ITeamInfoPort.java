package com.achobeta.domain.login.adapter.port;

import com.achobeta.domain.team.model.entity.TeamEntity;

import java.util.List;

public interface ITeamInfoPort {

    /**
     * 查询用户团队信息
     * @param userId 用户id
     * @return 用户团队信息
     */
    List<TeamEntity> queryTeamByUserId(String userId);
}
