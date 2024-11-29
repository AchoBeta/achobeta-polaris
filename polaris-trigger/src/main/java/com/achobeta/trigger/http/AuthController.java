package com.achobeta.trigger.http;

import com.achobeta.api.dto.AuthRequestDTO;
import com.achobeta.api.dto.ListRoleRequestDTO;
import com.achobeta.api.dto.ListRoleResponseDTO;
import com.achobeta.domain.auth.model.entity.RoleEntity;
import com.achobeta.domain.auth.service.IRoleService;
import com.achobeta.types.Response;
import com.achobeta.types.annotation.AuthVerify;
import com.achobeta.types.common.Constants;
import com.achobeta.types.constraint.LoginVerification;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yangzhiyao
 * @description AuthController 鉴权测试接口
 * @date 2024/11/22
 */
@Slf4j
@Validated
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final IRoleService roleService;

    /**
     * 测试鉴权接口
     * @param authRequestDTO
     * @return
     */
    @GetMapping("test")
    @LoginVerification
    @AuthVerify("TEAM_DELETE")
    public Response test(@Valid AuthRequestDTO authRequestDTO) {
        log.info("进入鉴权测试接口，参数：{}", authRequestDTO);

        return Response.SYSTEM_SUCCESS();
    }

    /**
     * 查询可以支配赋予他人的团队及角色
     * @param listRoleRequestDTO
     * @return 角色列表
     */
    @GetMapping("role")
    @AuthVerify("AUTH:ROLE_LIST")
    public Response<ListRoleResponseDTO> queryRoles(@Valid ListRoleRequestDTO listRoleRequestDTO) {
        try {
            String userId = listRoleRequestDTO.getUserId();
            log.info("用户访问团队成员信息详情服务开始，{}", userId);

            List<RoleEntity> roles = roleService.queryRoles(userId);
            log.info("用户访问团队成员信息详情服务结束，{}", userId);

            return Response.SYSTEM_SUCCESS(ListRoleResponseDTO.builder().roles(roles).build());
        } catch (AppException e) {
            log.error("用户访问团队成员信息详情服务失败！userId: {}", listRoleRequestDTO.getUserId(), e);
            return Response.<ListRoleResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("用户访问团队成员信息详情服务失败！userId: {}", listRoleRequestDTO.getUserId(), e);
            return Response.SERVICE_ERROR();
        }
    }
}
