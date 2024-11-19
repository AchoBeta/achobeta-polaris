package com.achobeta.api;

import com.achobeta.api.dto.AddMemberRequestDTO;
import com.achobeta.api.dto.AddMemberResponseDTO;
import com.achobeta.types.Response;

/**
 * @author yangzhiyao
 * @description 团队服务接口
 * @date 2024/11/7
 */
public interface ITeamService {

    /**
     * 添加团队成员接口
     * @param requestDTO AddMemberRequestDTO
     * @return Response<AddMemberResponseDTO>
     */
    Response<AddMemberResponseDTO> addMember(AddMemberRequestDTO requestDTO);

}
