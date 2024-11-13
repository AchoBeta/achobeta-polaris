package com.achobeta.api;

import com.achobeta.api.dto.ModifyStructureRequestDTO;
import com.achobeta.api.dto.ModifyStructureResponseDTO;
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
     * 修改团队组织架构接口
     * @param modifyStructureRequestDTO
     * @return
     * @author yangzhiyao
     * @date 2024/11/12
     */
    Response<ModifyStructureResponseDTO> modifyStructure(@Valid @RequestBody ModifyStructureRequestDTO modifyStructureRequestDTO);

}
