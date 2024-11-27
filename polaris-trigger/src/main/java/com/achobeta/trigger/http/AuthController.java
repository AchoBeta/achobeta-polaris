package com.achobeta.trigger.http;

import com.achobeta.api.dto.AuthRequestDTO;
import com.achobeta.types.Response;
import com.achobeta.types.annotation.AuthVerify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    /**
     * 测试鉴权接口
     * @param authRequestDTO
     * @return
     */
    @GetMapping("test")
    @AuthVerify("TEAM_DELETE")
    public Response test(@Valid AuthRequestDTO authRequestDTO) {
        log.info("进入鉴权测试接口，参数：{}", authRequestDTO);

        return Response.SYSTEM_SUCCESS();
    }
}
