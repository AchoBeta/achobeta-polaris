package com.achobeta.api;

import com.achobeta.api.dto.QueryMemberInfoRequestDTO;
import com.achobeta.api.dto.QueryMemberInfoResponseDTO;
import com.achobeta.types.Response;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author yangzhiyao
 * @description 团队服务接口
 * @date 2024/11/7
 */
public interface ITeamService {

    /**
     * 查询团队成员信息详情接口
     * @param requestDTO 查询请求参数，包含用户ID、团队ID，成员ID
     * @return 成员个人信息
     */
    Response<QueryMemberInfoResponseDTO> queryMemberInfo(@Valid @RequestBody QueryMemberInfoRequestDTO requestDTO);

}
