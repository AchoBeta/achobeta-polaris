package com.achobeta.api;

import com.achobeta.api.dto.QueryStructureRequestDTO;
import com.achobeta.api.dto.QueryStructureResponseDTO;
import com.achobeta.types.Response;

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
    Response<QueryStructureResponseDTO> queryStructure(QueryStructureRequestDTO structureRequestDTO);

}
