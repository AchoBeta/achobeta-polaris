package com.achobeta.trigger.http;

import com.achobeta.api.ITeamService;
import com.achobeta.api.dto.ModifyMemberInfoRequestDTO;
import com.achobeta.api.dto.ModifyMemberInfoResponseDTO;
import com.achobeta.domain.team.service.IMemberService;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.types.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
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
    @PutMapping("member")
    public Response<ModifyMemberInfoResponseDTO> modifyMemberInfo(ModifyMemberInfoRequestDTO requestDTO) {
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
                        .build(), requestDTO.getAddPositions(), requestDTO.getDeletePositions());

        return Response.SYSTEM_SUCCESS(ModifyMemberInfoResponseDTO.builder().userInfo(requestDTO).build());
    }

}
