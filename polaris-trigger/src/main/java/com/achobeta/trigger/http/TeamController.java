package com.achobeta.trigger.http;

import com.achobeta.api.ITeamService;
import com.achobeta.api.dto.QueryMemberInfoRequestDTO;
import com.achobeta.api.dto.QueryMemberInfoResponseDTO;
import com.achobeta.domain.team.service.IMemberService;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.types.Response;
import com.achobeta.types.common.Constants;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    /**
     * 查看团队成员信息详情接口
     */
    @GetMapping("/member/detail")
    @Override
    public Response<QueryMemberInfoResponseDTO> queryMemberInfo(@Valid QueryMemberInfoRequestDTO requestDTO) {
        try {
            log.info("用户访问个人中心信息页面系统开始，{}", requestDTO);

            UserEntity userEntity = memberService.queryMemberInfo(requestDTO.getMemberId());
            log.info("用户访问个人中心信息页面系统结束，{}", requestDTO);

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
            log.error("用户访问个人中心信息页面系统失败！{}", requestDTO, e);
            return Response.<QueryMemberInfoResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("用户访问个人中心信息页面系统失败！{}", requestDTO, e);
            return Response.SERVICE_ERROR();
        }
    }
}
