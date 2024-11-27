package com.achobeta.trigger.http;

import com.achobeta.api.ITeamService;
import com.achobeta.api.dto.*;
import com.achobeta.api.dto.team.RequestMemberListDTO;
import com.achobeta.api.dto.team.ResponseMemberListDTO;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.domain.team.service.IMemberService;
import com.achobeta.domain.team.service.IStructureService;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.types.Response;
import com.achobeta.types.annotation.AuthVerify;
import com.achobeta.types.common.Constants;
import com.achobeta.types.constraint.LoginVerification;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    private final IStructureService structureService;
  
    /**
     * 删除团队成员
     * @param requestDTO
     * @return
     */
    @Override
    @DeleteMapping("member")
    @LoginVerification
    @AuthVerify("MEMBER:MEMBER_DELETE")
    public Response<DeleteMemberResponseDTO> deleteMember(@Valid DeleteMemberRequestDTO requestDTO) {
        try {
            log.info("用户访问添加团队成员接口，requestDTO:{}", requestDTO);

            memberService.deleteMember(requestDTO.getUserId(), requestDTO.getMemberId(), requestDTO.getTeamId());

            log.info("用户访问添加团队成员接口，requestDTO:{}", requestDTO);
            return Response.SYSTEM_SUCCESS(DeleteMemberResponseDTO.builder()
                            .userId(requestDTO.getUserId())
                            .teamId(requestDTO.getTeamId())
                            .memberId(requestDTO.getMemberId())
                            .build());
        } catch (AppException e) {
            log.error("用户访问添加团队成员接口失败！{}, error:{}",
                    requestDTO, e.toString(), e);
            return Response.<DeleteMemberResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
        }catch (Exception e) {
          log.error("访问添加团队成员接口失败！",e);
            return Response.SYSTEM_FAIL();
        }
    }
  
    /**
     * 添加团队成员接口
     * @author yangzhiyao
     * @date 2024/11/19
     * @param requestDTO AddMemberRequestDTO
     * @return 用户实体，状态码 0-成功，1-已存在用户
     */
    @Override
    @PostMapping("member")
    @LoginVerification
    @AuthVerify("MEMBER:MEMBER_ADD")
    public Response<AddMemberResponseDTO> addMember(@Valid @RequestBody AddMemberRequestDTO requestDTO) {
        try {
            log.info("访问添加团队成员接口开始, userId:{}, phone:{}, teamId:{}",requestDTO.getUserId(),requestDTO.getPhone(),requestDTO.getTeamId());

            UserEntity userEntity = UserEntity.builder()
                        .userName(requestDTO.getUserName())
                        .phone(requestDTO.getPhone())
                        .gender(requestDTO.getGender())
                        .idCard(requestDTO.getIdCard())
                        .email(requestDTO.getEmail())
                        .grade(requestDTO.getGrade())
                        .major(requestDTO.getMajor())
                        .studentId(requestDTO.getStudentId())
                        .experience(requestDTO.getExperience())
                        .currentStatus(requestDTO.getCurrentStatus())
                        .entryTime(requestDTO.getEntryTime())
                        .roles(requestDTO.getRoles())
                        .build();
            userEntity = memberService.addMember(userEntity,
                    requestDTO.getUserId(),
                    requestDTO.getTeamId(),
                    requestDTO.getPositions());
            Integer statusCode = userEntity.getLikeCount() == null ? 0 : 1;

            log.info("访问添加团队成员接口结束userId:{}, phone:{}, teamId:{}",requestDTO.getUserId(),requestDTO.getPhone(),requestDTO.getTeamId());
            return Response.SYSTEM_SUCCESS(AddMemberResponseDTO.builder()
                                        .userInfo(userEntity)
                                        .statusCode(statusCode)
                                        .build());
        } catch (AppException e) {
            log.error("用户访问添加团队成员接口失败！{}, error:{}",
                    requestDTO, e.toString(), e);
            return Response.<AddMemberResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
          }catch (Exception e) {
            log.error("访问添加团队成员接口失败！",e);
            return Response.SYSTEM_FAIL();
        }
    }

    /**
     * 修改团队成员信息
     * @param requestDTO
     * @return 修改信息
     */
    @Override
    @PutMapping("member/detail")
    @LoginVerification
    @AuthVerify("MEMBER:MEMBER_MODIFY")
    public Response<ModifyMemberInfoResponseDTO> modifyMemberInfo(@Valid @RequestBody ModifyMemberInfoRequestDTO requestDTO) {
        String teamId = requestDTO.getTeamId();
        String memberId = requestDTO.getMemberId();
        log.info("用户访问修改团队成员信息接口，userId：{}, memberId：{}, teamId:{}", requestDTO.getUserId(), memberId, teamId);

        memberService.modifyMember(teamId, UserEntity.builder()
                        .phone(requestDTO.getPhone())
                        .entryTime(requestDTO.getEntryTime())
                        .userId(memberId)
                        .userName(requestDTO.getUserName())
                        .gender(requestDTO.getGender())
                        .idCard(requestDTO.getIdCard())
                        .email(requestDTO.getEmail())
                        .grade(requestDTO.getGrade())
                        .major(requestDTO.getMajor())
                        .studentId(requestDTO.getStudentId())
                        .experience(requestDTO.getExperience())
                        .currentStatus(requestDTO.getCurrentStatus())
                        .roles(requestDTO.getRoles())
                        .build(), requestDTO.getAddPositions(), requestDTO.getDeletePositions());

        return Response.SYSTEM_SUCCESS(ModifyMemberInfoResponseDTO.builder().userInfo(requestDTO).build());
    }
  
    /**
     * 查看团队成员信息详情接口
     */
    @GetMapping("/member/detail")
    @LoginVerification
    @Override
    public Response<QueryMemberInfoResponseDTO> queryMemberInfo(@Valid QueryMemberInfoRequestDTO requestDTO) {
        try {
            log.info("用户访问团队成员信息详情服务开始，{}", requestDTO);

            UserEntity userEntity = memberService.queryMemberInfo(requestDTO.getMemberId());
            log.info("用户访问团队成员信息详情服务结束，{}", requestDTO);

            return Response.SYSTEM_SUCCESS(QueryMemberInfoResponseDTO.builder()
                    .userId(userEntity.getUserId())
                    .userName(userEntity.getUserName())
                    .phone(userEntity.getPhone())
                    .gender(userEntity.getGender())
                    .idCard(userEntity.getIdCard())
                    .email(userEntity.getEmail())
                    .grade(userEntity.getGrade())
                    .major(userEntity.getMajor())
                    .studentId(userEntity.getStudentId())
                    .experience(userEntity.getExperience())
                    .currentStatus(userEntity.getCurrentStatus())
                    .entryTime(userEntity.getEntryTime())
                    .likeCount(userEntity.getLikeCount())
                    .liked(userEntity.getLiked())
                    .positions(userEntity.getPositions())
                    .build());
        } catch (AppException e) {
            log.error("用户访问团队成员信息详情服务失败！{}", requestDTO, e);
            return Response.<QueryMemberInfoResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("用户访问团队成员信息详情服务失败！{}", requestDTO, e);
            return Response.SERVICE_ERROR();
        }
    }

    /**
     * 修改团队组织架构
     * @param modifyStructureRequestDTO
     * @return
     */
    @PutMapping("structure")
    @Override
    @LoginVerification
    @AuthVerify("STRUCTURE:STRUCTURE_MODIFY")
    public Response<ModifyStructureResponseDTO> modifyStructure(@Valid @RequestBody ModifyStructureRequestDTO modifyStructureRequestDTO) {
        try {
            log.info("用户访问团队管理系统修改团队组织架构开始，userId:{} teamId:{}",
                    modifyStructureRequestDTO.getUserId(), modifyStructureRequestDTO.getTeamId());

            List<LinkedHashMap<String, Object>> tempAddPositions = (List<LinkedHashMap<String, Object>>) modifyStructureRequestDTO.getAddPositions();
            List<LinkedHashMap<String, Object>> tempDeletePositions = (List<LinkedHashMap<String, Object>>) modifyStructureRequestDTO.getDeletePositions();
            List<PositionEntity> addPositions = new ArrayList<>();
            List<PositionEntity> deletePositions = new ArrayList<>();
            for (LinkedHashMap<String, Object> obj : tempAddPositions) {
                PositionEntity positionEntity = PositionEntity.builder()
                        .positionId((String) obj.get("parentPositionId"))
                        .positionName((String) obj.get("parentPositionName"))
                        .level((Integer) obj.get("parentPositionLevel"))
                        .subordinateName((String) obj.get("newPositionName"))
                        .build();
                addPositions.add(positionEntity);
            }
            for (LinkedHashMap<String, Object> obj: tempDeletePositions) {
                PositionEntity positionEntity = PositionEntity.builder()
                        .positionId((String) obj.get("positionId"))
                        .level((Integer) obj.get("level"))
                        .build();
                deletePositions.add(positionEntity);
            }

            List<PositionEntity> resultPositions = structureService.modifyStructure(addPositions, deletePositions, modifyStructureRequestDTO.getTeamId());

            log.info("用户访问团队管理系统修改团队组织架构结束，userId:{} teamId:{}",
                    modifyStructureRequestDTO.getUserId(), modifyStructureRequestDTO.getTeamId());
            return Response.SYSTEM_SUCCESS(ModifyStructureResponseDTO.builder()
                    .addPositions(resultPositions)
                    .build());
        } catch (AppException e) {
            log.error("修改团队组织架构失败！userId:{}, teamId:{}, 可知原因error:{}",
                    modifyStructureRequestDTO.getUserId(), modifyStructureRequestDTO.getTeamId(), e.toString(), e);
            return Response.<ModifyStructureResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("修改团队组织架构失败！userId:{}, teamId:{}, 未知异常error:{}",
                    modifyStructureRequestDTO.getUserId(), modifyStructureRequestDTO.getTeamId(), e.toString(), e);
            return Response.SERVICE_ERROR();
        }
    }

    /**
     * 查询团队成员列表
     * @param requestMemberListDTO
     * @return
     */
    @Override
    @LoginVerification
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
     * @param querystructureRequestDTO 入参包括用户id和团队id
     * @return
     */
    @GetMapping("structure")
    @Override
    @LoginVerification
    @AuthVerify("STRUCTURE:STRUCTURE_VIEW")
    public Response<QueryStructureResponseDTO> queryStructure(@Valid QueryStructureRequestDTO querystructureRequestDTO) {
        try {
            log.info("用户访问团队管理系统查询团队组织架构开始，userId:{} teamId:{}",
                    querystructureRequestDTO.getUserId(), querystructureRequestDTO.getTeamId());

            PositionEntity positionEntity = structureService
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
