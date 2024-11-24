package com.achobeta.trigger.http;

import com.achobeta.api.ITeamService;
import com.achobeta.api.dto.team.RequestMemberListDTO;
import com.achobeta.api.dto.team.ResponseMemberListDTO;
import com.achobeta.domain.team.service.IMemberService;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.api.dto.QueryStructureRequestDTO;
import com.achobeta.api.dto.QueryStructureResponseDTO;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.domain.team.service.IStructureService;
import com.achobeta.types.Response;
import com.achobeta.types.common.Constants;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

/**
 * @author yangzhiyao
 * @date 2024/11/8
 */
@Slf4j
@Validated
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/team/")
@RequiredArgsConstructor
public class TeamController implements ITeamService {

    private final IMemberService memberService;

    private final IStructureService viewStructureService;

    /**
     * 查询团队成员列表
     *
     * @param requestMemberListDTO
     * @return
     */
    @Override
    @GetMapping("/member/list")
    public Response<ResponseMemberListDTO> queryMemberList(@Valid RequestMemberListDTO requestMemberListDTO) {
        try {
            log.info("用户访问查询团队成员列表开始，{}", requestMemberListDTO);

            List<UserEntity> members = memberService
                    .queryMembers(requestMemberListDTO.getTeamId(),
                            requestMemberListDTO.getLastId(),
                            requestMemberListDTO.getLimit());

            log.info("用户访问查询团队成员列表结束，{}", requestMemberListDTO);
            return Response.SYSTEM_SUCCESS(ResponseMemberListDTO.builder()
                    .members(members)
                    .build());
        } catch (AppException e) {
            log.error("用户访问查询团队成员列表失败！{}, error:{}",
                    requestMemberListDTO, e.toString(), e);
            return Response.<ResponseMemberListDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("用户访问查询团队成员列表失败！{}, 未知异常error:{}",
                    requestMemberListDTO, e.toString(), e);
            return Response.SERVICE_ERROR();
        }
    }


    /**
     * 查询团队组织架构
     *
     * @param querystructureRequestDTO 入参包括用户id和团队id
     * @return
     */
    @GetMapping("structure")
    @Override
    public Response<QueryStructureResponseDTO> queryStructure(@Valid QueryStructureRequestDTO querystructureRequestDTO) {
        try {
            log.info("用户访问团队管理系统查询团队组织架构开始，userId:{} teamId:{}",
                    querystructureRequestDTO.getUserId(), querystructureRequestDTO.getTeamId());

            PositionEntity positionEntity = viewStructureService
                    .queryStructure(querystructureRequestDTO.getTeamId());

            log.info("用户访问团队管理系统查询团队组织架构结束，userId:{} teamId:{}",
                    querystructureRequestDTO.getUserId(), querystructureRequestDTO.getTeamId());

            return Response.SYSTEM_SUCCESS(QueryStructureResponseDTO.builder()
                    .positionId(positionEntity.getPositionId())
                    .positionName(positionEntity.getPositionName())
                    .teamId(positionEntity.getTeamId())
                    .level(positionEntity.getLevel())
                    .subordinates(positionEntity.getSubordinates())
                    .build());
        } catch (AppException e) {
            log.error("用户访问团队管理系统失败！userId:{}, teamId:{}, 已知异常error:{}",
                    querystructureRequestDTO.getUserId(), querystructureRequestDTO.getTeamId(), e.toString(), e);
            return Response.<QueryStructureResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("用户访问团队管理系统失败！userId:{}, teamId:{}, 未知异常error:{}",
                    querystructureRequestDTO.getUserId(), querystructureRequestDTO.getTeamId(), e.toString(), e);
            return Response.SERVICE_ERROR();
        }
    }
}
