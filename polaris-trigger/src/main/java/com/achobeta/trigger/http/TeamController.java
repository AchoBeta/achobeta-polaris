package com.achobeta.trigger.http;

import com.achobeta.api.ITeamService;
import com.achobeta.api.dto.QueryStructureRequestDTO;
import com.achobeta.api.dto.QueryStructureResponseDTO;
import com.achobeta.api.response.Response;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.domain.team.service.IViewStructureService;
import com.achobeta.types.common.Constants;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

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
     * @param queryStructureRequestDTO
     * @return Response<QueryStructureResponseDTO>
     * @date 2024/11/10
     */
    @GetMapping("structure")
    @Override
    public Response<QueryStructureResponseDTO> queryStructure(@RequestBody QueryStructureRequestDTO queryStructureRequestDTO) {
        try {
            log.info("用户访问团队管理系统开始，userId:{} teamId:{}",
                    queryStructureRequestDTO.getUserId(), queryStructureRequestDTO.getTeamId());

            PositionEntity positionEntity = viewStructureService
                    .queryStructure(queryStructureRequestDTO.getTeamId());

            log.info("用户访问团队管理系统结束，userId:{} teamId:{}",
                    queryStructureRequestDTO.getUserId(), queryStructureRequestDTO.getTeamId());

            return Response.<QueryStructureResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(QueryStructureResponseDTO.builder()
                            .positionId(positionEntity.getPositionId())
                            .positionName(positionEntity.getPositionName())
                            .teamId(positionEntity.getTeamId())
                            .level(positionEntity.getLevel())
                            .subordinates(positionEntity.getSubordinates())
                            .build())
                    .build();
        } catch (AppException e) {
            log.error("用户访问团队管理系统失败！userId:{}, teamId:{}, 已知异常error:{}",
                    queryStructureRequestDTO.getUserId(), queryStructureRequestDTO.getTeamId(), e.toString(), e);
            return Response.<QueryStructureResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("用户访问团队管理系统失败！userId:{}, teamId:{}, 未知异常error:{}",
                    queryStructureRequestDTO.getUserId(), queryStructureRequestDTO.getTeamId(), e.toString(), e);
            return Response.<QueryStructureResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.NO_PERMISSIONS.getCode())
                    .info(Constants.ResponseCode.NO_PERMISSIONS.getInfo())
                    .build();
        }
    }
}
