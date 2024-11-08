package com.achobeta.trigger.http;

import com.achobeta.api.IGetCodeService;
import com.achobeta.api.dto.GetCodeRequestDTO;
import com.achobeta.api.dto.GetCodeResponseDTO;
import com.achobeta.api.response.Response;
import com.achobeta.domain.login.model.valobj.SendCodeVO;
import com.achobeta.domain.login.service.ISendCodeService;
import com.achobeta.types.common.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 严豪哲
 * @Description: 获取验证码控制器
 * @Date: 2024/11/5 23:35
 * @Version: 1.0
 */

@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class GetCodeController implements IGetCodeService {

    private final ISendCodeService sendCodeService;
    @GetMapping(value = "getcode")
    @Override
    public Response<GetCodeResponseDTO> getCode(GetCodeRequestDTO getCodeRequestDTO) {
        try {
            log.info("用户访问获取验证码系统开始，phone:{}", getCodeRequestDTO.getPhone());

            // 调用验证码服务发送验证码
            SendCodeVO sendCodeVO = sendCodeService.sendCode(getCodeRequestDTO.getPhone());
            log.info("用户访问获取验证码系统结束，phone:{}", getCodeRequestDTO.getPhone());
            return Response.<GetCodeResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(GetCodeResponseDTO.builder()
                            .result(sendCodeVO.getResult())
                            .build())
                    .build();
        } catch (Exception e) {
            log.error("用户访问获取验证码系统失败,phone:{}", getCodeRequestDTO.getPhone(), e);
            return Response.<GetCodeResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(e.getMessage())
                    .build();
        }

    }
}
