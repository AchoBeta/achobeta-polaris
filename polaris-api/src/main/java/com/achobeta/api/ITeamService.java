package com.achobeta.api;

import com.achobeta.api.dto.team.RequestMemberListDTO;
import com.achobeta.api.dto.team.ResponseMemberListDTO;
import com.achobeta.types.Response;

import javax.validation.Valid;

/**
 * @author yangzhiyao
 * @description 团队服务接口
 * @date 2024/11/7
 */
public interface ITeamService {

    /**
     * 查询团队成员列表
     * @param requestMemberListDTO 请求参数: userId, teamId, lastId, limit
     * @return 团队成员列表
     */
    Response<ResponseMemberListDTO> queryMemberList(@Valid RequestMemberListDTO requestMemberListDTO);

}
