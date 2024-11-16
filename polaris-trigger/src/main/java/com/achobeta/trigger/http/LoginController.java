package com.achobeta.trigger.http;

import com.achobeta.api.ILoginService;
import com.achobeta.api.dto.LoginRequestDTO;
import com.achobeta.api.dto.LoginResponseDTO;
import com.achobeta.domain.login.model.valobj.LoginVO;
import com.achobeta.domain.login.service.IAuthorizationService;
import com.achobeta.types.Response;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.util.DeviceNameUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: 严豪哲
 * @Description:
 * @Date: 2024/11/9 10:36
 * @Version: 1.0
 */

@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/auth/")
@RequiredArgsConstructor
public class LoginController implements ILoginService {

    @Resource
    private IAuthorizationService authorizationService;

    @Override
    @PostMapping(value = "login")
    public Response<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("用户访问登录系统开始，phone:{}", loginRequestDTO.getPhone());

            // 从请求头中获取设备信息
            String userAgent = request.getHeader("User-Agent");
            String deviceName = DeviceNameUtil.getDeviceName(userAgent);

            // 调用登录服务进行登录
            LoginVO loginVO = authorizationService.login(loginRequestDTO.getPhone(), loginRequestDTO.getCode(), loginRequestDTO.getIp(), loginRequestDTO.isAutoLogin(),deviceName);

            // 将access_token和refresh_token添加到Cookie中
            Cookie accessTokenCookie = new Cookie("access_token", loginVO.getAccessToken());
            Cookie refreshTokenCookie = new Cookie("refresh_token", loginVO.getRefreshToken());

//            // 设置Cookie的过期时间（可选）
//            accessTokenCookie.setMaxAge(60); // 1小时
//            refreshTokenCookie.setMaxAge(60); // 7天86400 * 7

            // 设置Cookie的路径（可选）
            accessTokenCookie.setPath("/achobeta/polaris/access_token");
            refreshTokenCookie.setPath("/achobeta/polaris/refresh_token");

            // 将Cookie添加到响应中
            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);

            //todo
//            RedirectView redirectView = new RedirectView();
//            redirectView.setUrl("/achobeta/polaris/home");

            log.info("用户访问登录系统结束，phone:{}", loginRequestDTO.getPhone());
            return Response.SYSTEM_SUCCESS(
                    LoginResponseDTO.builder()
                    .phone(loginVO.getPhone())
                    .build()
            );
        } catch (AppException e) {
            log.error("用户访问登录系统失败,phone:{}", loginRequestDTO.getPhone(), e);
            return Response.SERVICE_ERROR(e.getInfo());
        }catch (Exception e) {
            log.error("用户访问登录系统失败,phone:{}", loginRequestDTO.getPhone(), e);
            return Response.SERVICE_ERROR(e.getMessage());
        }
    }
}
