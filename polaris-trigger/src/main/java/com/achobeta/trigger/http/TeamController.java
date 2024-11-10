package com.achobeta.trigger.http;

import com.achobeta.api.ITeamService;
import com.achobeta.api.dto.StructureRequestDTO;
import com.achobeta.api.dto.StructureResponseDTO;
import com.achobeta.api.response.Response;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.domain.team.service.IViewStructureService;
import com.achobeta.types.common.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * @author yangzhiyao
 * @date 2024/11/8
 */
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/team/")
@RequiredArgsConstructor
public class TeamController implements ITeamService {
    
    private final IViewStructureService viewStructureService;

    /**
     * 查询团队组织架构
     * @param structureRequestDTO
     * @return Response<StructureResponseDTO>
     * @date 2024/11/10
     */
    @GetMapping("structure")
    @Override
    public Response<StructureResponseDTO> structure(@RequestBody StructureRequestDTO structureRequestDTO) {
        try {
            log.info("用户访问团队管理系统开始，userId:{} teamName:{}",
                    structureRequestDTO.getUserId(), structureRequestDTO.getTeamName());

            PositionEntity positionEntity = viewStructureService
                    .queryStructure(structureRequestDTO.getTeamName());

            log.info("用户访问团队管理系统结束，userId:{} teamName:{}",
                    structureRequestDTO.getUserId(), structureRequestDTO.getTeamName());

            return Response.<StructureResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(StructureResponseDTO.builder()
                            .positionId(positionEntity.getPositionId())
                            .positionName(positionEntity.getPositionName())
                            .teamName(positionEntity.getTeamName())
                            .level(positionEntity.getLevel())
                            .subordinates(Collections.singletonList(positionEntity.getSubordinates()))
                            .build())
                    .build();
        } catch (Exception e) {
            log.error("用户访问团队管理系统失败！userId:{} teamName:{}",
                    structureRequestDTO.getUserId(), structureRequestDTO.getTeamName(), e);
            return Response.<StructureResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.NO_PERMISSIONS.getCode())
                    .info(Constants.ResponseCode.NO_PERMISSIONS.getInfo())
                    .build();
        }
    }
}
