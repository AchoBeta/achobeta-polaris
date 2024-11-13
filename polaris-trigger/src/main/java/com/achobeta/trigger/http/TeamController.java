package com.achobeta.trigger.http;

import com.achobeta.api.ITeamService;
import com.achobeta.api.dto.ModifyStructureRequestDTO;
import com.achobeta.api.dto.ModifyStructureResponseDTO;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.domain.team.service.IModifyStructureService;
import com.achobeta.types.Response;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@CrossOrigin("${app.config.cross-origin}:*")
@RequestMapping("/api/${app.config.api-version}/team/")
@RequiredArgsConstructor
public class TeamController implements ITeamService {

    private final IModifyStructureService modifyStructureService;

    /**
     * 修改团队组织架构
     * @param modifyStructureRequestDTO
     * @return
     */
    @PutMapping("structure")
    @Override
    public Response<ModifyStructureResponseDTO> modifyStructure(@Valid @RequestBody ModifyStructureRequestDTO modifyStructureRequestDTO) {
        try {
            log.info("用户访问团队管理系统修改团队组织架构开始，userId:{} teamId:{}",
                    modifyStructureRequestDTO.getUserId(), modifyStructureRequestDTO.getTeamId());

            List<Object> tempAddPositions = modifyStructureRequestDTO.getAddPositions();
            List<Object> tempDeletePositions = modifyStructureRequestDTO.getDeletePositions();
            List<PositionEntity> addPositions = new ArrayList<>();
            List<PositionEntity> deletePositions = new ArrayList<>();
            for (Object obj : tempAddPositions) {
                PositionEntity positionEntity = PositionEntity.builder()
                        .positionId(((LinkedHashMap<String, String>) obj).get("parentPositionId"))
                        .positionName(((LinkedHashMap<String, String>) obj).get("parentPositionName"))
                        .level(Byte.valueOf(((LinkedHashMap<String, String>) obj).get("parentPositionLevel")))
                        .subordinateName(((LinkedHashMap<String, String>) obj).get("newPositionName"))
                        .build();
                addPositions.add(positionEntity);
            }
            for (Object obj: tempDeletePositions) {
                PositionEntity positionEntity = PositionEntity.builder()
                        .positionId(((LinkedHashMap<String, String>) obj).get("positionId"))
                        .level(((LinkedHashMap<String, Byte>) obj).get("level"))
                        .build();
                deletePositions.add(positionEntity);
            }

            List<PositionEntity> resultPositions = modifyStructureService.modifyStructure(addPositions, deletePositions, modifyStructureRequestDTO.getTeamId());

            log.info("用户访问团队管理系统修改团队组织架构结束，userId:{} teamId:{}",
                    modifyStructureRequestDTO.getUserId(), modifyStructureRequestDTO.getTeamId());
            return Response.SYSTEM_SUCCESS(ModifyStructureResponseDTO.builder()
                    .addPositions(resultPositions)
                    .build());
        } catch (AppException e) {
            log.error("修改团队组织架构失败！userId:{}, teamId:{}, 可知原因error:{}",
                    modifyStructureRequestDTO.getUserId(), modifyStructureRequestDTO.getTeamId(), e.toString(), e);
            return Response.SERVICE_ERROR(e.getInfo());
        } catch (Exception e) {
            log.error("修改团队组织架构失败！userId:{}, teamId:{}, 未知异常error:{}",
                    modifyStructureRequestDTO.getUserId(), modifyStructureRequestDTO.getTeamId(), e.toString(), e);
            return Response.SERVICE_ERROR();
        }
    }
}
