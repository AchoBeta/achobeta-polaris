package com.achobeta.trigger.http;

import com.achobeta.api.dto.announce.GetUserAnnounceRequestDTO;
import com.achobeta.api.dto.announce.GetUserAnnounceResponseDTO;
import com.achobeta.api.response.Response;
import com.achobeta.domain.announce.model.valobj.UserAnnounceVO;
import com.achobeta.domain.announce.service.IAnnounceService;
import com.achobeta.types.common.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangwenxing
 * @date 2024/11/11
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}:*")
@RequestMapping("/api/${app.config.api-version}/announce/")
@RequiredArgsConstructor
public class AnnounceController implements com.achobeta.api.IAnnounceService {
    private final IAnnounceService service;

    /**
     *
     * @param getUserAnnounceRequestDTO
     * @return
     */
    @GetMapping("/getUserAnnounce")
    @Override
    public Response<GetUserAnnounceResponseDTO> getUserAnnounce(GetUserAnnounceRequestDTO getUserAnnounceRequestDTO) {
        try {
            log.info("用户访问文本渲染系统开始，userId:{} limit:{} lastAnnounceId:{}",
                    getUserAnnounceRequestDTO.getUserId(), getUserAnnounceRequestDTO.getLimit(), getUserAnnounceRequestDTO.getLastAnnounceId());

            UserAnnounceVO userAnnounceVO = service.queryAnnouncesByUserId(getUserAnnounceRequestDTO.getUserId(), getUserAnnounceRequestDTO.getLimit(), getUserAnnounceRequestDTO.getLastAnnounceId());


            log.info("用户访问文本渲染系统结束，announceEntities:{} more:{}",userAnnounceVO.getAnnounceEntities(),userAnnounceVO.isMore() );

            return Response.<GetUserAnnounceResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(GetUserAnnounceResponseDTO.builder()
                            .userAnnounce(userAnnounceVO.getAnnounceEntities())
                            .more(userAnnounceVO.isMore())
                            .build())
                    .build();

        } catch (Exception e) {
            log.error("用户访问文本渲染系统失败！userId:{} limit:{} lastAnnounceId:{}",
                    getUserAnnounceRequestDTO.getUserId(), getUserAnnounceRequestDTO.getLimit(), getUserAnnounceRequestDTO.getLastAnnounceId(), e);
            return Response.<GetUserAnnounceResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.NO_PERMISSIONS.getCode())
                    .info(Constants.ResponseCode.NO_PERMISSIONS.getInfo())
                    .build();
        }
    }
}
