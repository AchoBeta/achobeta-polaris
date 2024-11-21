package com.achobeta.api;

import com.achobeta.api.dto.QueryStructureRequestDTO;
import com.achobeta.api.dto.QueryStructureResponseDTO;
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
     * 查看团队组织架构接口
     * @param structureRequestDTO 入参包括用户id和团队id
     * @return 团队组织架构信息
     * @author yangzhiyao
     * @date 2024/11/10
     */
    Response<QueryStructureResponseDTO> queryStructure(@Valid QueryStructureRequestDTO structureRequestDTO);

}
