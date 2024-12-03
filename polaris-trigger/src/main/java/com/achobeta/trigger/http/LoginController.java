package com.achobeta.trigger.http;

import com.achobeta.api.ILoginService;
import com.achobeta.api.dto.LoginRequestDTO;
import com.achobeta.api.dto.LoginResponseDTO;
import com.achobeta.domain.login.model.valobj.LoginVO;
import com.achobeta.domain.login.service.IAuthorizationService;
import com.achobeta.domain.login.service.IRefreshTokenService;
import com.achobeta.types.Response;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.util.DeviceNameUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@Validated
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/auth/")
@RequiredArgsConstructor
public class LoginController implements ILoginService {

    @Resource
    private IAuthorizationService authorizationService;

    @Resource
    private IRefreshTokenService reflashTokenService;


    private final String ACCESS_TOKEN = "access_token";
    private final String REFRESH_TOKEN = "refresh_token";
    private final String ACCESS_TOKEN_PATH = "/achobeta/polaris/access_token";
    private final String REFRESH_TOKEN_PATH = "/achobeta/polaris/refresh_token";

    @Override
    @PostMapping(value = "login")
    public Response<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        try {
            loginRequestDTO.setIp("127.0.0.1");
            log.info("用户访问登录系统开始,phone:{}", loginRequestDTO.getPhone());

            if(loginRequestDTO.getIsAutoLogin().equals("true")){
                log.info("用户选择自动登录,phone:{}", loginRequestDTO.getPhone());
                loginRequestDTO.setAutoLogin(true);
            } else {
                log.info("用户选择非自动登录,phone:{}", loginRequestDTO.getPhone());
                loginRequestDTO.setAutoLogin(false);
            }

            // 从请求头中获取设备信息
            String userAgent = request.getHeader("User-Agent");
            String deviceName = DeviceNameUtil.getDeviceName(userAgent);

            // 调用登录服务进行登录
            LoginVO loginVO = authorizationService.login(loginRequestDTO.getPhone(), loginRequestDTO.getCode(), loginRequestDTO.getIp(), loginRequestDTO.isAutoLogin(), deviceName, loginRequestDTO.getFingerPrinting());


            //把token放在响应体中
//            // 将access_token和refresh_token添加到Cookie中
//            Cookie accessTokenCookie = new Cookie(ACCESS_TOKEN, loginVO.getAccessToken());
//            Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN, loginVO.getRefreshToken());
//
////            // 设置Cookie的过期时间（可选）
////            accessTokenCookie.setMaxAge(60); // 1小时
////            refreshTokenCookie.setMaxAge(60); // 7天86400 * 7
//
////            //设置Cookie只能以https的形式发送
////            accessTokenCookie.setSecure(true);
////            refreshTokenCookie.setSecure(true);
//
//            // 设置Cookie的路径（可选）
//            accessTokenCookie.setPath(ACCESS_TOKEN_PATH);
//            refreshTokenCookie.setPath(REFRESH_TOKEN_PATH);
//
//            // 将Cookie添加到响应中
//            response.addCookie(accessTokenCookie);
//            response.addCookie(refreshTokenCookie);

            log.info("用户访问登录系统结束，phone:{}", loginRequestDTO.getPhone());
            return Response.SYSTEM_SUCCESS(
                    LoginResponseDTO.builder()
                            .phone(loginVO.getPhone())
                            .userId(loginVO.getUserId())
                            .accessToken(loginVO.getAccessToken())
                            .refreshToken(loginVO.getRefreshToken())
                            .teams(loginVO.getTeams())
                            .deviceId(loginVO.getDeviceId())
                            .build()
            );
        } catch (AppException e) {
            log.error("用户访问登录系统失败,phone:{}", loginRequestDTO.getPhone(), e);
            return Response.APP_ECEPTION(e);
        }catch (Exception e) {
            log.error("用户访问登录系统失败,phone:{}", loginRequestDTO.getPhone(), e);
            return Response.SERVICE_ERROR(e.getMessage());
        }
    }

    @Override
    @PostMapping(value = "refresh")
    public Response<LoginResponseDTO> refresh(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 从请求头中获取refreshToken
            String refrashToken = request.getHeader(REFRESH_TOKEN);

            log.info("正在访问无感刷新接口,reflashToken:{}", refrashToken);
            if (null == refrashToken || refrashToken.isEmpty()) {
                log.info("refreshToken缺失");
                throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFLASH_TOKEN_MISSING.getCode()), GlobalServiceStatusCode.LOGIN_REFLASH_TOKEN_MISSING.getMessage());
            }
            LoginVO loginVO = reflashTokenService.reflash(refrashToken);

            // 把token放在响应体中
//            // 将access_token添加到Cookie中
//            Cookie accessTokenCookie = new Cookie(ACCESS_TOKEN, loginVO.getAccessToken());
//
////            // 设置Cookie的过期时间（可选）
////            accessTokenCookie.setMaxAge(60); // 1小时
//
////            //设置Cookie只能以https的形式发送
////            accessTokenCookie.setSecure(true);
//
//            // 设置Cookie的路径（可选）
//            accessTokenCookie.setPath(ACCESS_TOKEN_PATH);
//
//            // 将Cookie添加到响应中
//            response.addCookie(accessTokenCookie);

            return Response.SYSTEM_SUCCESS(
                    LoginResponseDTO.builder()
                            .phone(loginVO.getPhone())
                            .userId(loginVO.getUserId())
                            .accessToken(loginVO.getAccessToken())
                            .teams(loginVO.getTeams())
                            .deviceId(loginVO.getDeviceId())
                            .build()
            );
        } catch (AppException e) {
            log.info("访问无感刷新接口失败,reflashToken:{}", request.getHeader(REFRESH_TOKEN));

            return Response.APP_ECEPTION(e);
        }catch (Exception e) {
            log.info("访问无感刷新接口失败,reflashToken:{}", request.getHeader(REFRESH_TOKEN));

            return Response.SERVICE_ERROR(e.getMessage());
        }
    }
}
