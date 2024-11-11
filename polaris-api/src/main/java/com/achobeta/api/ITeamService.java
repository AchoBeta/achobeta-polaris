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
    /**
     * 查看团队组织架构接口
     * @param structureRequestDTO 入参包括用户id和团队id
     * @return 团队组织架构接口
     */
    Response<StructureResponseDTO> structure(StructureRequestDTO structureRequestDTO);

}
