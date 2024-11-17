package com.achobeta.api;

import com.achobeta.api.dto.ModifyMemberInfoRequestDTO;
import com.achobeta.api.dto.ModifyMemberInfoResponseDTO;
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
     * 修改成员信息
     * @param requestDTO
     * @return
     */
    Response<ModifyMemberInfoResponseDTO> modifyMemberInfo(@Valid @RequestBody ModifyMemberInfoRequestDTO requestDTO);

}
