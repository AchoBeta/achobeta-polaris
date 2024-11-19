package com.achobeta.trigger.http;

import com.achobeta.api.ITeamService;
import com.achobeta.api.dto.AddMemberRequestDTO;
import com.achobeta.api.dto.AddMemberResponseDTO;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.domain.team.service.IMemberService;
import com.achobeta.types.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final IMemberService memberService;

    @Override
    public Response<AddMemberResponseDTO> addMember(AddMemberRequestDTO requestDTO) {
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
                        .build();
            userEntity = memberService.addMember(userEntity,
                    requestDTO.getUserId(),
                    requestDTO.getTeamId(),
                    requestDTO.getPositions());
            Integer statusCode = userEntity.getUserId() == null ? 0 : 1;

            log.info("访问添加团队成员接口结束userId:{}, phone:{}, teamId:{}",requestDTO.getUserId(),requestDTO.getPhone(),requestDTO.getTeamId());
            return Response.SYSTEM_SUCCESS(AddMemberResponseDTO.builder()
                                        .userInfo(userEntity)
                                        .statusCode(statusCode)
                                        .build());
        } catch (Exception e) {
            log.error("访问添加团队成员接口失败！",e);
            return Response.SYSTEM_FAIL();
        }
    }
}
