package com.achobeta.api;

import com.achobeta.api.dto.ModifyMemberInfoRequestDTO;
import com.achobeta.api.dto.ModifyMemberInfoResponseDTO;
import com.achobeta.api.dto.QueryMemberInfoRequestDTO;
import com.achobeta.api.dto.QueryMemberInfoResponseDTO;
import com.achobeta.api.dto.team.RequestMemberListDTO;
import com.achobeta.api.dto.team.ResponseMemberListDTO;
import com.achobeta.api.dto.ModifyStructureRequestDTO;
import com.achobeta.api.dto.ModifyStructureResponseDTO;
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
     * 修改成员信息
     * @param requestDTO
     * @return
     */
    Response<ModifyMemberInfoResponseDTO> modifyMemberInfo(@Valid @RequestBody ModifyMemberInfoRequestDTO requestDTO);

    /**
     * 查询团队成员信息详情接口
     * @param requestDTO 查询请求参数，包含用户ID、团队ID，成员ID
     * @return 成员个人信息
     */
    Response<QueryMemberInfoResponseDTO> queryMemberInfo(@Valid QueryMemberInfoRequestDTO requestDTO);
  
    /**
     * 查询团队成员列表
     * @param requestMemberListDTO 请求参数: userId, teamId, lastId, limit
     * @return 团队成员列表
     */
    Response<ResponseMemberListDTO> queryMemberList(@Valid RequestMemberListDTO requestMemberListDTO);
  
   /**
     * 修改团队组织架构接口
     * @param modifyStructureRequestDTO
     * @return
     * @author yangzhiyao
     * @date 2024/11/12
     */
    Response<ModifyStructureResponseDTO> modifyStructure(@Valid @RequestBody ModifyStructureRequestDTO modifyStructureRequestDTO);
  
    /**
     * 查看团队组织架构接口
     * @param structureRequestDTO 入参包括用户id和团队id
     * @return 团队组织架构信息
     * @author yangzhiyao
     * @date 2024/11/10
     */
    Response<QueryStructureResponseDTO> queryStructure(@Valid QueryStructureRequestDTO structureRequestDTO);

}
