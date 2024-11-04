package com.achobeta.trigger.http;

import com.achobeta.api.IReadService;
import com.achobeta.api.dto.ReadRequestDTO;
import com.achobeta.api.response.Response;
import com.achobeta.read.UserInfo;
import com.achobeta.read.good_design.ReadService;
import com.achobeta.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author BanTanger 半糖
 * @date 2024/11/4
 */
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/read/")
public class ReadController implements IReadService {

    @Resource
    private ReadService readService;

    /**
     * <pre>
     * ###
     * POST http://localhost:8091/api/v1/read/test
     * Content-Type: application/json
     *
     * {
     *   "name": "AchoBeta 5.0",
     *   "isAuth": true,
     *   "textId": "10001"
     * }
     * </pre>
     * <pre>
     * mac curl 方式
     * curl --location --request POST 'http://127.0.0.1:8091/api/v1/read/test' \
     * --header 'User-Agent: Apifox/1.0.0 (https://apifox.com)' \
     * --header 'Content-Type: application/json' \
     * --header 'Host:127.0.0.1:8091' \
     * --header 'Connection:keep-alive' \
     * --data-raw '{
     *      "name":"AchoBeta 5.0",
     *      "isAuth":true,
     *      "textId":"10001"
     *   }'
     * </pre>
     * <pre>
     * window curl 方式
     * curl --location --request POST "http://127.0.0.1:8091/api/v1/read/test" ^
     *      --header "User-Agent: Apifox/1.0.0 (https://apifox.com)" ^
     *      --header "Content-Type: application/json" ^
     *      --header "Host: 127.0.0.1:8091"^
     *      --header "Connection: keep-alive"^
     *      --data-raw "{    \"name\": \"AchoBeta 5.0\",    \"isAuth\": true,    \"textId\": \"10001\"  }"
     * </pre>
     */
    @PostMapping(value = "test")
    @Override
    public Response<String> read(@RequestBody ReadRequestDTO readRequestDTO) {
        try {
            log.info("用户来访，访问文本渲染系统开始，username:{} isAuth:{} textId:{}",
                    readRequestDTO.getName(), readRequestDTO.getIsAuth(), readRequestDTO.getTextId());
            String textContent = readService.readTemplate(UserInfo.builder()
                    .name(readRequestDTO.getName())
                    .isAuth(readRequestDTO.getIsAuth())
                    .build(), readRequestDTO.getTextId());
            log.info("用户来访，访问文本渲染系统结束，username:{} isAuth:{} textId:{} textContent:{}",
                    readRequestDTO.getName(), readRequestDTO.getIsAuth(), readRequestDTO.getTextId(), textContent);
            return Response.<String>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(textContent)
                    .build();
        } catch (Exception e) {
            log.error("用户来访，访问文本渲染系统失败，username:{} isAuth:{} textId:{}",
                    readRequestDTO.getName(), readRequestDTO.getIsAuth(), readRequestDTO.getTextId(), e);
            return Response.<String>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
