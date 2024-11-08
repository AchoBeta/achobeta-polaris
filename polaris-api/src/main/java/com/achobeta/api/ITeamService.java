package com.achobeta.api;

import com.achobeta.api.dto.StructureRequestDTO;
import com.achobeta.api.dto.StructureResponseDTO;
import com.achobeta.api.response.Response;

/**
 * @author yangzhiyao
 * @description 团队服务接口
 * @date 2024/11/7
 */
public interface ITeamService {

    Response<StructureResponseDTO> structure(StructureRequestDTO structureRequestDTO);

}
