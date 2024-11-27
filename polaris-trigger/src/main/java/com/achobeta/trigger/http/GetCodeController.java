package com.achobeta.trigger.http;

import com.achobeta.api.IGetCodeService;
import com.achobeta.api.dto.GetCodeRequestDTO;
import com.achobeta.api.dto.GetCodeResponseDTO;
import com.achobeta.domain.login.service.ISendCodeService;
import com.achobeta.types.Response;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: 严豪哲
 * @Description: 获取验证码控制器
 * @Date: 2024/11/5 23:35
 * @Version: 1.0
 */

@Slf4j
@RestController()
@Validated
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/auth/")
@RequiredArgsConstructor
public class GetCodeController implements IGetCodeService {

    private final ISendCodeService sendCodeService;

    @PostMapping(value = "getcode")
    @Override
    public Response<GetCodeResponseDTO> getCode(@Valid @RequestBody GetCodeRequestDTO getCodeRequestDTO) {
        try {
            log.info("用户访问获取验证码系统开始，phone:{}", getCodeRequestDTO.getPhone());

            // 调用验证码服务发送验证码
            sendCodeService.sendCode(getCodeRequestDTO.getPhone());
            log.info("用户访问获取验证码系统结束，phone:{}", getCodeRequestDTO.getPhone());
            return Response.SYSTEM_SUCCESS();
        } catch (AppException e) {
            log.error("用户访问获取验证码系统失败,phone:{}", getCodeRequestDTO.getPhone(), e);
            return Response.APP_ECEPTION(e);
        } catch (Exception e) {
            log.error("用户访问获取验证码系统失败,phone:{}", getCodeRequestDTO.getPhone(), e);
            return Response.SERVICE_ERROR(e.getMessage());
        }

    }
}
