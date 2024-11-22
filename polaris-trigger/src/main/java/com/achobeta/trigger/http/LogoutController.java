package com.achobeta.trigger.http;

import com.achobeta.api.ILogoutService;
import com.achobeta.api.dto.LogoutRequestDTO;
import com.achobeta.api.dto.LogoutResponseDTO;
import com.achobeta.domain.login.service.IUserLogoutService;
import com.achobeta.types.Response;
import com.achobeta.types.constraint.LoginVerification;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: 严豪哲
 * @Description: 登出接口控制器
 * @Date: 2024/11/19 19:03
 * @Version: 1.0
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/auth/")
@RequiredArgsConstructor
public class LogoutController implements ILogoutService {

    @Resource
    private IUserLogoutService logoutService;

    @LoginVerification
    @Override
    @RequestMapping(value = "logout")
    public Response<LogoutResponseDTO> logout(@Valid @RequestBody LogoutRequestDTO logoutRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        try {
            // 从请求头中获取refreshToken
            String accessToken = request.getHeader("access_token");
            log.info("用户访问登出接口开始,access_token:{}", accessToken);

            logoutService.logout(accessToken, logoutRequestDTO.getDeviceId());

            log.info("用户访问登出接口结束,access_token:{}", accessToken);
            return Response.SYSTEM_SUCCESS(
                    LogoutResponseDTO.builder()
                           .build()
            );
        } catch (AppException e) {
            log.info("用户访问登出接口失败,access_token:{}", request.getHeader("access_token"));

            return Response.SERVICE_ERROR(e.getInfo());
        }catch (Exception e) {
            log.info("用户访问登出接口失败,access_token:{}", request.getHeader("access_token"));

            return Response.SERVICE_ERROR(e.getMessage());
        }

    }

}
