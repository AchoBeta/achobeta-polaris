package com.achobeta.api;

import com.achobeta.api.dto.AddMemberRequestDTO;
import com.achobeta.api.dto.AddMemberResponseDTO;
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
     * 添加团队成员接口
     * @author yangzhiyao
     * @date 2024/11/19
     * @param requestDTO AddMemberRequestDTO
     * @return Response<AddMemberResponseDTO>
     */
    Response<AddMemberResponseDTO> addMember(@Valid @RequestBody AddMemberRequestDTO requestDTO);

}
