package com.achobeta.trigger.http;

import com.achobeta.api.ITeamService;
import com.achobeta.api.dto.DeleteMemberRequestDTO;
import com.achobeta.api.dto.DeleteMemberResponseDTO;
import com.achobeta.domain.team.service.IMemberService;
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
     * 删除团队成员
     * @param requestDTO
     * @return
     */
    @Override
    @DeleteMapping("member")
    public Response<DeleteMemberResponseDTO> deleteMember(@Valid @RequestBody DeleteMemberRequestDTO requestDTO) {
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

}
