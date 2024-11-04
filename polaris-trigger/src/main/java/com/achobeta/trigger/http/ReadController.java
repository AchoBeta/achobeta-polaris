package com.achobeta.trigger.http;

import com.achobeta.api.IReadService;
import com.achobeta.api.dto.RenderRequestDTO;
import com.achobeta.api.dto.RenderResponseDTO;
import com.achobeta.api.response.Response;
import com.achobeta.domain.render.model.valobj.RenderBookVO;
import com.achobeta.domain.render.service.IRenderTextService;
import com.achobeta.types.common.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

/**
 * @author BanTanger 半糖
 * @date 2024/11/4
 */
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/read/")
@RequiredArgsConstructor
public class ReadController implements IReadService {

    private final IRenderTextService renderTextService;

    /**
     * demo 接口，熟悉包结构
     * @param renderRequestDTO
     * @return
     */
    @PostMapping("render")
    @Override
    public Response<RenderResponseDTO> render(@RequestBody RenderRequestDTO renderRequestDTO) {
        try {
            log.info("用户访问文本渲染系统开始，userId:{} bookId:{}",
                    renderRequestDTO.getUserId(), renderRequestDTO.getBookId());

            RenderBookVO renderBookVO = renderTextService
                    .renderBook(renderRequestDTO.getUserId(), renderRequestDTO.getBookId());

            log.info("用户访问文本渲染系统结束，username:{} bookName:{} bookContent:{}",
                    renderBookVO.getUserName(), renderBookVO.getBookName(), renderBookVO.getBookContent());

            return Response.<RenderResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(RenderResponseDTO.builder()
                            .userName(renderBookVO.getUserName())
                            .bookName(renderBookVO.getBookName())
                            .bookContent(renderBookVO.getBookContent())
                            .build())
                    .build();
        } catch (Exception e) {
            log.error("用户访问文本渲染系统失败！userId:{} bookId:{}",
                    renderRequestDTO.getUserId(), renderRequestDTO.getBookId(), e);
            return Response.<RenderResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.NO_PERMISSIONS.getCode())
                    .info(Constants.ResponseCode.NO_PERMISSIONS.getInfo())
                    .build();
        }
    }

//    /**
//     * demo 接口，熟悉项目
//     * <pre>
//     * ###
//     * POST http://localhost:8091/api/v1/read/test
//     * Content-Type: application/json
//     *
//     * {
//     *   "name": "AchoBeta 5.0",
//     *   "isAuth": true,
//     *   "textId": "10001"
//     * }
//     * </pre>
//     * <pre>
//     * mac curl 方式
//     * curl --location --request POST 'http://127.0.0.1:8091/api/v1/read/test' \
//     * --header 'User-Agent: Apifox/1.0.0 (https://apifox.com)' \
//     * --header 'Content-Type: application/json' \
//     * --header 'Host:127.0.0.1:8091' \
//     * --header 'Connection:keep-alive' \
//     * --data-raw '{
//     *      "name":"AchoBeta 5.0",
//     *      "isAuth":true,
//     *      "textId":"10001"
//     *   }'
//     * </pre>
//     * <pre>
//     * window curl 方式
//     * curl --location --request POST "http://127.0.0.1:8091/api/v1/read/test" ^
//     *      --header "User-Agent: Apifox/1.0.0 (https://apifox.com)" ^
//     *      --header "Content-Type: application/json" ^
//     *      --header "Host: 127.0.0.1:8091"^
//     *      --header "Connection: keep-alive"^
//     *      --data-raw "{    \"name\": \"AchoBeta 5.0\",    \"isAuth\": true,    \"textId\": \"10001\"  }"
//     * </pre>
//     */
//    @PostMapping(value = "test")
//    @Override
//    public Response<String> read(@RequestBody ReadRequestDTO readRequestDTO) {
//        try {
//            log.info("用户来访，访问文本渲染系统开始，username:{} isAuth:{} textId:{}",
//                    readRequestDTO.getName(), readRequestDTO.getIsAuth(), readRequestDTO.getTextId());
//            String textContent = readService.readTemplate(UserInfo.builder()
//                    .name(readRequestDTO.getName())
//                    .isAuth(readRequestDTO.getIsAuth())
//                    .build(), readRequestDTO.getTextId());
//            log.info("用户来访，访问文本渲染系统结束，username:{} isAuth:{} textId:{} textContent:{}",
//                    readRequestDTO.getName(), readRequestDTO.getIsAuth(), readRequestDTO.getTextId(), textContent);
//            return Response.<String>builder()
//                    .traceId(MDC.get(Constants.TRACE_ID))
//                    .code(Constants.ResponseCode.SUCCESS.getCode())
//                    .info(Constants.ResponseCode.SUCCESS.getInfo())
//                    .data(textContent)
//                    .build();
//        } catch (Exception e) {
//            log.error("用户来访，访问文本渲染系统失败，username:{} isAuth:{} textId:{}",
//                    readRequestDTO.getName(), readRequestDTO.getIsAuth(), readRequestDTO.getTextId(), e);
//            return Response.<String>builder()
//                    .traceId(MDC.get(Constants.TRACE_ID))
//                    .code(Constants.ResponseCode.UN_ERROR.getCode())
//                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
//                    .build();
//        }
//    }

}
