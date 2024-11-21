package com.achobeta.api;

import com.achobeta.api.dto.DeleteMemberRequestDTO;
import com.achobeta.api.dto.DeleteMemberResponseDTO;
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
     * 删除团队成员接口
     * @author yangzhiyao
     * @date 2024/11/21
     * @param requestDTO
     * @return userId, memberId, teamId
     */
    Response<DeleteMemberResponseDTO> deleteMember(@Valid DeleteMemberRequestDTO requestDTO);

}
