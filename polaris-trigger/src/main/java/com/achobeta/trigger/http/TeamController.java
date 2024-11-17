package com.achobeta.trigger.http;

import com.achobeta.api.ITeamService;
import com.achobeta.api.dto.team.RequestMemberListDTO;
import com.achobeta.api.dto.team.ResponseMemberListDTO;
import com.achobeta.domain.team.service.IMemberService;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.types.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@CrossOrigin("${app.config.cross-origin}:*")
@RequestMapping("/api/${app.config.api-version}/team/")
@RequiredArgsConstructor
public class TeamController implements ITeamService {

    private final IMemberService memberService;

    @Override
    @GetMapping("/member")
    public Response<ResponseMemberListDTO> queryMemberList(@Valid @RequestBody RequestMemberListDTO requestMemberListDTO) {
        log.info("用户访问团队管理系统开始，teamId：{}",requestMemberListDTO.getTeamId());

        List<UserEntity> members = memberService
                .queryMembers(requestMemberListDTO.getTeamId(),
                        requestMemberListDTO.getLastId(),
                        requestMemberListDTO.getLimit());

        log.info("用户访问团队管理系统结束，teamId：{}",requestMemberListDTO.getTeamId());
        return Response.SYSTEM_SUCCESS(ResponseMemberListDTO.builder()
                .members(members)
                .build());
    }
}
